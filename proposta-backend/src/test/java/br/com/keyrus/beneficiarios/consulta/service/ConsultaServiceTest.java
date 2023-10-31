package br.com.keyrus.beneficiarios.consulta.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import br.com.keyrus.beneficiarios.domain.Consulta;
import br.com.keyrus.beneficiarios.domain.Especialidade;
import br.com.keyrus.beneficiarios.dto.BeneficiarioDTO;
import br.com.keyrus.beneficiarios.dto.ConsultaDTO;
import br.com.keyrus.beneficiarios.dto.ConsultaListDTO;
import br.com.keyrus.beneficiarios.exceptions.BeneficiarioNaoCadastradoException;
import br.com.keyrus.beneficiarios.exceptions.ConflitoAgendamentoException;
import br.com.keyrus.beneficiarios.exceptions.EspecialidadeException;
import br.com.keyrus.beneficiarios.mapper.BeneficiarioMapper;
import br.com.keyrus.beneficiarios.mapper.ConsultaMapper;
import br.com.keyrus.beneficiarios.repository.ConsultaRepository;
import br.com.keyrus.beneficiarios.service.BeneficiarioService;
import br.com.keyrus.beneficiarios.service.ConsultaService;
import br.com.keyrus.beneficiarios.service.EspecialidadeService;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ConsultaServiceTest {
	
	@Mock
	private ConsultaRepository repository;
	
	@Mock
	private ConsultaMapper mapper;
	
	@InjectMocks
	private ConsultaService service;
	
    @Mock
    private BeneficiarioService beneficiarioService;

    @Mock
    private BeneficiarioMapper beneficiarioMapper;

    @Mock
    private EspecialidadeService especialidadeService;
	
    @Test
    void testValidarAgendamentoComConflito() {
        // Mock o repository para retornar um Optional com um valor (ou seja, consulta já existe)
        when(repository.getConsultaByBeneficiarioCpfAndDataHora(anyString(), any(LocalDateTime.class)))
            .thenReturn(Optional.of(new Consulta())); // Substitua "Consulta" pelo tipo correto

        // Executar o método e verificar que uma exceção de ConflitoAgendamentoException é lançada
        assertThrows(ConflitoAgendamentoException.class, () -> service.validarAgendamento("CPF_DO_BENEFICIARIO", LocalDateTime.now()));
    }
    
    @Test
    void testCadastrarConsultaComSucesso() throws ConflitoAgendamentoException, BeneficiarioNaoCadastradoException, EspecialidadeException {
        ConsultaDTO consultaDTO = new ConsultaDTO(); // Crie um DTO de consulta
        Consulta consulta = new Consulta(); // Crie uma consulta vazia

        // Configurar mocks para retornar valores adequados e não lançar exceções
        when(mapper.toEntity(consultaDTO)).thenReturn(consulta);
        when(beneficiarioService.consultarBeneficiario(consultaDTO.getBeneficiario())).thenReturn(new BeneficiarioDTO());
        when(especialidadeService.buscarEspecialidadePorNome(consultaDTO.getEspecialidade())).thenReturn(new Especialidade());

        // Executar o método
        ConsultaDTO response = service.cadastrarConsulta(consultaDTO);

        // Verificar resultados
        assertNotNull(response);
    }

    @Test
    void testCadastrarConsultaComConflitoAgendamento() throws ConflitoAgendamentoException, BeneficiarioNaoCadastradoException, EspecialidadeException {
        ConsultaDTO consultaDTO = new ConsultaDTO(); // Crie um DTO de consulta
        Consulta consulta = new Consulta(); // Crie uma consulta vazia

        // Configurar mocks para lançar uma exceção ao validar o agendamento
        when(beneficiarioService.consultarBeneficiario(consultaDTO.getBeneficiario())).thenReturn(new BeneficiarioDTO());
        when(especialidadeService.buscarEspecialidadePorNome(consultaDTO.getEspecialidade())).thenReturn(new Especialidade());

        // Configurar o mock para simular um conflito de agendamento
        when(repository.getConsultaByBeneficiarioCpfAndDataHora(anyString(), any(LocalDateTime.class)))
                .thenReturn(Optional.empty());

        // Executar o método e verificar que uma exceção de ConflitoAgendamentoException é lançada
        assertThrows(ConflitoAgendamentoException.class, () -> service.cadastrarConsulta(consultaDTO));
    }

    @Test
    void testCadastrarConsultaComBeneficiarioNaoCadastrado() throws BeneficiarioNaoCadastradoException, EspecialidadeException {
        ConsultaDTO consultaDTO = new ConsultaDTO(); // Crie um DTO de consulta
        Consulta consulta = new Consulta(); // Crie uma consulta vazia

        // Configurar mocks para lançar uma exceção ao consultar o beneficiário
        when(mapper.toEntity(consultaDTO)).thenReturn(consulta);
        when(especialidadeService.buscarEspecialidadePorNome(consultaDTO.getEspecialidade())).thenReturn(new Especialidade());

        doThrow(new BeneficiarioNaoCadastradoException("Beneficiário não cadastrado")).when(beneficiarioService).consultarBeneficiario(consultaDTO.getBeneficiario());

        assertThrows(ConflitoAgendamentoException.class, () -> service.cadastrarConsulta(consultaDTO));
    }
    
    @Test
    void testLimparBaseComSucesso() {
        // Configurar mock para simular uma operação de exclusão bem-sucedida
        doNothing().when(repository).deleteAll();

        // Executar o método
        service.limparBase();

        // Verificar que o método deleteAll foi chamado uma vez
        verify(repository, times(1)).deleteAll();
    }

    @Test
    void testLimparBaseComExcecao() {
        // Configurar mock para simular uma exceção ao excluir
        doThrow(new RuntimeException("Erro ao excluir consulta")).when(repository).deleteAll();

        // Executar o método
        service.limparBase();

        // Verificar que o método deleteAll foi chamado uma vez
        verify(repository, times(1)).deleteAll();
    }
    
    @Test
    void testExcluirConsultaComSucesso() {
        Integer idConsulta = 1; // ID da consulta a ser excluída

        // Configurar mock para simular uma operação de exclusão bem-sucedida
        doNothing().when(repository).deleteById(idConsulta);

        // Executar o método
        service.excluirConsulta(idConsulta);

        // Verificar que o método deleteById foi chamado uma vez com o ID especificado
        verify(repository, times(1)).deleteById(idConsulta);
    }

    @Test
    void testExcluirConsultaComExcecao() {
        Integer idConsulta = 1; // ID da consulta a ser excluída

        // Configurar mock para simular uma exceção ao excluir
        doThrow(new RuntimeException("Erro ao excluir consulta")).when(repository).deleteById(idConsulta);

        // Executar o método
        service.excluirConsulta(idConsulta);

        // Verificar que o método deleteById foi chamado uma vez com o ID especificado
        verify(repository, times(1)).deleteById(idConsulta);
    }
    
    @Test
    void testListarConsultasComSucesso() {
        String cpfBeneficiario = "12345678900"; // CPF do beneficiário
        Consulta consulta1 = new Consulta(); // Cria consultas correspondentes
        Consulta consulta2 = new Consulta();
        List<Consulta> consultas = new ArrayList<>();
        consultas.add(consulta1);
        consultas.add(consulta2);

        ConsultaDTO consultaDto1 = new ConsultaDTO(); // Crie DTOs correspondentes
        ConsultaDTO consultaDto2 = new ConsultaDTO();
        List<ConsultaDTO> consultaDtos = new ArrayList<>();
        consultaDtos.add(consultaDto1);
        consultaDtos.add(consultaDto2);

        ConsultaListDTO expectedResponse = new ConsultaListDTO();
        expectedResponse.setBeneficiario(cpfBeneficiario);
        expectedResponse.setConsultas(consultaDtos);

        // Configurar mocks
        when(repository.getConsultasByBeneficiarioCpf(cpfBeneficiario)).thenReturn(consultas);
        when(mapper.toDTO(consulta1)).thenReturn(consultaDto1);
        when(mapper.toDTO(consulta2)).thenReturn(consultaDto2);

        // Executar o método
        ConsultaListDTO response = service.listarConsultas(cpfBeneficiario);

        // Verificar resultados
        assertNotNull(response);
        assertEquals(expectedResponse.getBeneficiario(), response.getBeneficiario());
        assertEquals(expectedResponse.getConsultas().size(), response.getConsultas().size());
    }

    @Test
    void testListarConsultasComExcecao() {
        String cpfBeneficiario = "12345678900"; // CPF do beneficiário

        // Configurar mock para simular uma exceção ao listar consultas
        when(repository.getConsultasByBeneficiarioCpf(cpfBeneficiario)).thenThrow(new RuntimeException("Erro ao listar consultas"));

        // Executar o método
        ConsultaListDTO response = service.listarConsultas(cpfBeneficiario);

        // Verificar que a lista de consultas está vazia devido à exceção
        assertNotNull(response);
        assertEquals(cpfBeneficiario, response.getBeneficiario());
    }
	
    
}

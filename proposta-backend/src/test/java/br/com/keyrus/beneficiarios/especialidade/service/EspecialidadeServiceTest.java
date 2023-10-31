package br.com.keyrus.beneficiarios.especialidade.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import br.com.keyrus.beneficiarios.domain.Especialidade;
import br.com.keyrus.beneficiarios.dto.EspecialidadeDTO;
import br.com.keyrus.beneficiarios.exceptions.EspecialidadeException;
import br.com.keyrus.beneficiarios.mapper.EspecialidadeMapper;
import br.com.keyrus.beneficiarios.repository.EspecialidadeRepository;
import br.com.keyrus.beneficiarios.service.EspecialidadeService;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class EspecialidadeServiceTest {
	
	@Mock
	private EspecialidadeMapper mapper;

	@Mock
	private EspecialidadeRepository repository;
	
	@InjectMocks
	private EspecialidadeService service;
	
	@Test
	void testBuscaEspecialidadePorNome() throws EspecialidadeException {
		String nomeEspecialidade = "Cardiologia";
        Especialidade especialidadeMock = new Especialidade();
        especialidadeMock.setNomeEspecialidade(nomeEspecialidade);

        when(repository.findEspecialidadeByNomeEspecialidade(nomeEspecialidade)).thenReturn(Optional.of(especialidadeMock));

        // Executando o método que queremos testar
        Especialidade resultado = service.buscarEspecialidadePorNome(nomeEspecialidade);

        // Verificando o resultado
        assertEquals(nomeEspecialidade, resultado.getNomeEspecialidade());

        // Verificando as interações com o mock
        verify(repository, times(1)).findEspecialidadeByNomeEspecialidade(nomeEspecialidade);
   }
	
	@Test
    void testBuscarEspecialidadePorNomeEspecialidadeNaoEncontrada() {
        // Preparando o cenário do teste
        String nomeEspecialidade = "Cardiologia";

        when(repository.findEspecialidadeByNomeEspecialidade(nomeEspecialidade)).thenReturn(Optional.empty());

        // Executando o método que queremos testar e verificando a exceção
        assertThrows(EspecialidadeException.class, () -> service.buscarEspecialidadePorNome(nomeEspecialidade));
    }

    @Test
    void testBuscarEspecialidadePorNomeComExcecao() {
        // Preparando o cenário do teste
        String nomeEspecialidade = "Cardiologia";

        when(repository.findEspecialidadeByNomeEspecialidade(nomeEspecialidade)).thenThrow(new RuntimeException("Erro no repositório"));

        // Executando o método que queremos testar e verificando a exceção
        assertThrows(EspecialidadeException.class, () -> service.buscarEspecialidadePorNome(nomeEspecialidade));
    }
    
    @Test
    void testListarTodasEspecialidades() throws EspecialidadeException {
        List<Especialidade> especialidades = new ArrayList<>();
        especialidades.add(new Especialidade("Cardiologia"));
        especialidades.add(new Especialidade("Dermatologia"));

        when(repository.findAll()).thenReturn(especialidades);

        // Executando o método que queremos testar
        List<EspecialidadeDTO> resultado = service.listarTodasEspecialidades();

        // Verificando o resultado
        assertEquals(especialidades.size(), resultado.size());

        // Verificando as interações com o mock
        verify(repository, times(1)).findAll();    	
    }
    

    @Test
    void testListarTodasEspecialidadesComExcecao() {
        // Preparando o cenário do teste
        when(repository.findAll()).thenThrow(new RuntimeException("Erro no repositório"));

        // Executando o método que queremos testar e verificando a exceção
        assertThrows(EspecialidadeException.class, () -> service.listarTodasEspecialidades());
        
    }
}

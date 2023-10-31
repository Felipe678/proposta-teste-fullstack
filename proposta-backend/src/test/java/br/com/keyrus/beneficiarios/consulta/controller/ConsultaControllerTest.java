package br.com.keyrus.beneficiarios.consulta.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.keyrus.beneficiarios.controller.ConsultaController;
import br.com.keyrus.beneficiarios.dto.ConsultaDTO;
import br.com.keyrus.beneficiarios.dto.ConsultaListDTO;
import br.com.keyrus.beneficiarios.exceptions.ConflitoAgendamentoException;
import br.com.keyrus.beneficiarios.repository.ConsultaRepository;
import br.com.keyrus.beneficiarios.service.ConsultaService;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ConsultaControllerTest {

	@Mock
	private ConsultaDTO consultaDto;
	
	@Mock
	private ConsultaListDTO consultaList;
	
	@Mock
	private ConsultaRepository repository;
	
	@Mock
	private ConsultaService service;
	
	@InjectMocks
	private ConsultaController controller;
	
	private String cpfBeneficiario = "12345678901";
	
	@Test
	void testCadastrarConsulta() throws ConflitoAgendamentoException {
		when(service.cadastrarConsulta(consultaDto)).thenReturn(consultaDto);
		
		ResponseEntity<Object> response = controller.cadastrarConsulta(consultaDto);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	void testCadastrarConsultaJaCadastrada() throws ConflitoAgendamentoException {
		when(service.cadastrarConsulta(consultaDto)).thenThrow(new ConflitoAgendamentoException("Conflito de Agendas. Foi encontrado outro agendamento para o mesmo dia e horario."));

		ResponseEntity<Object> response = controller.cadastrarConsulta(consultaDto);
		
		assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
	}
	
	@Test
	void testListarConsultas() {
		when(service.listarConsultas(cpfBeneficiario)).thenReturn(consultaList);
		
		ResponseEntity<ConsultaListDTO> response = controller.listarConsultas(cpfBeneficiario);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(consultaList, response.getBody());
	}
	
	@Test
	void testExcluirConsultas() {
		service.excluirConsulta(1);
		
		Mockito.verify(service, Mockito.times(1)).excluirConsulta(1);
		
		ResponseEntity<?> response = controller.excluirConsulta(1);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		
	}
	
}

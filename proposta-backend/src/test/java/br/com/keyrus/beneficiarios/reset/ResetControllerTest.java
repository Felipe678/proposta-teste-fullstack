package br.com.keyrus.beneficiarios.reset;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

import br.com.keyrus.beneficiarios.controller.ResetController;
import br.com.keyrus.beneficiarios.exceptions.BeneficiarioNaoCadastradoException;
import br.com.keyrus.beneficiarios.service.BeneficiarioService;
import br.com.keyrus.beneficiarios.service.ConsultaService;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ResetControllerTest {

	@Mock
	private BeneficiarioService beneficiarioService;
	
	@Mock
	private ConsultaService consultaService;
	
	@InjectMocks
	private ResetController controller;
	
	@Test
	void excluirTodosBeneficiariosEConsultas() throws BeneficiarioNaoCadastradoException {
		consultaService.limparBase();
		Mockito.verify(consultaService).limparBase();
		
		beneficiarioService.limparBase();
		Mockito.verify(beneficiarioService).limparBase();
		
		ResponseEntity<?> response = controller.excluirBeneficariosEConsultas();
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
}

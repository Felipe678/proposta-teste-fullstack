package br.com.keyrus.beneficiarios.especialidade.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import br.com.keyrus.beneficiarios.controller.EspecialidadeController;
import br.com.keyrus.beneficiarios.dto.EspecialidadeDTO;
import br.com.keyrus.beneficiarios.exceptions.EspecialidadeException;
import br.com.keyrus.beneficiarios.repository.EspecialidadeRepository;
import br.com.keyrus.beneficiarios.service.EspecialidadeService;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class EspecialidadeControllerTest {

	@Mock
	private EspecialidadeRepository repository;
	
	@Mock
	private EspecialidadeService service;
	
	@InjectMocks
	private EspecialidadeController controller;
	
	@Test
	void testListarEspecialidades() throws EspecialidadeException {
		List<EspecialidadeDTO> especialidades = new ArrayList<>();
		
		when(service.listarTodasEspecialidades()).thenReturn(especialidades);
		
		ResponseEntity<List<EspecialidadeDTO>> response = controller.listarEspecialidades();
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(especialidades, response.getBody());
	}
	
}

package br.com.keyrus.beneficiarios.beneficiario.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import br.com.keyrus.beneficiarios.controller.BeneficiarioController;
import br.com.keyrus.beneficiarios.domain.Beneficiario;
import br.com.keyrus.beneficiarios.dto.BeneficiarioDTO;
import br.com.keyrus.beneficiarios.exceptions.BeneficiarioCadastradoException;
import br.com.keyrus.beneficiarios.exceptions.BeneficiarioNaoCadastradoException;
import br.com.keyrus.beneficiarios.exceptions.CpfException;
import br.com.keyrus.beneficiarios.repository.BeneficiarioRepository;
import br.com.keyrus.beneficiarios.service.BeneficiarioService;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class BeneficiarioControllerTest {
	
	@Mock
	private BeneficiarioRepository repository;
	
	@Mock
    private BeneficiarioService service;

    @InjectMocks
    private BeneficiarioController controller;
    
    private String cpfBeneficiario = "12345678901";
    
    @Test
    void testConsultarBeneficiario() throws BeneficiarioNaoCadastradoException {
        
        BeneficiarioDTO beneficiario = new BeneficiarioDTO();
        beneficiario.setCpf(cpfBeneficiario);
        beneficiario.setDataNascimento(LocalDate.now().toString());
        beneficiario.setNome("Marcos Teste");

        Optional<Beneficiario> beneficiarioEntity = Optional.of(new Beneficiario());
        
        when(repository.getBeneficiarioByCpf(cpfBeneficiario)).thenReturn(beneficiarioEntity);
        
        when(service.consultarBeneficiario(cpfBeneficiario)).thenReturn(beneficiario);

        ResponseEntity<Object> response = controller.consultarBeneficiario(cpfBeneficiario);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(beneficiario, (BeneficiarioDTO)response.getBody());
    }

    @Test
    void testConsultarBeneficiarioCpfInvalido() throws BeneficiarioNaoCadastradoException, CpfException {

        when(service.consultarBeneficiario(cpfBeneficiario)).thenThrow(new BeneficiarioNaoCadastradoException("Beneficiario não cadastrado"));

        ResponseEntity<Object> response = controller.consultarBeneficiario(cpfBeneficiario);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Beneficiario não cadastrado", response.getBody());
    }
    
    @Test
    void testDeletarBeneficiario() {
    	ResponseEntity<Object> response = controller.deletarBeneficiario(cpfBeneficiario);
    	assertEquals(HttpStatus.OK, response.getStatusCode());
//    	service.excluirBeneficiario(cpfBeneficiario);
//    	Mockito.verify(service).excluirBeneficiario(cpfBeneficiario);
//    	service.excluirBeneficiario(cpfBeneficiario);
//    	Mockito.verify(service).excluirBeneficiario(cpfBeneficiario);
//    	Mockito.verify(service, Mockito.times(1)).excluirBeneficiario(cpfBeneficiario);
    	
    }
    
    @Test
    void testDeletarBeneficiarioInvalido() {
    	ResponseEntity<Object> response = controller.deletarBeneficiario("111111");
    	assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    
    @Test
    void testDeletarBeneficiarioNulo() {
    	ResponseEntity<Object> response = controller.deletarBeneficiario(null);
    	assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    
    @Test
    void testCadastrarBeneficiario() throws BeneficiarioCadastradoException {
    	BeneficiarioDTO beneficiario = new BeneficiarioDTO();
        beneficiario.setCpf(cpfBeneficiario);
        beneficiario.setDataNascimento(LocalDate.now().toString());
        beneficiario.setNome("Marcos Teste");
        
        when(service.cadastrarBeneficiario(beneficiario)).thenReturn(beneficiario);
        
        ResponseEntity<Object> response = controller.cadastrarBeneficiario(beneficiario);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    
    @Test
    void testCadastrarBeneficiarioCadastrado() throws BeneficiarioCadastradoException {
    	BeneficiarioDTO beneficiario = new BeneficiarioDTO();
        beneficiario.setCpf(cpfBeneficiario);
        beneficiario.setDataNascimento(LocalDate.now().toString());
        beneficiario.setNome("Marcos Teste");
        
        when(service.cadastrarBeneficiario(beneficiario)).thenThrow(new BeneficiarioCadastradoException("Beneficiario já cadastrado"));
        
        ResponseEntity<Object> response = controller.cadastrarBeneficiario(beneficiario);
        
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
    
}

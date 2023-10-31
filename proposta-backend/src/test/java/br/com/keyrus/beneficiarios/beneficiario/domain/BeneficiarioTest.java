package br.com.keyrus.beneficiarios.beneficiario.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.keyrus.beneficiarios.domain.Beneficiario;
import br.com.keyrus.beneficiarios.repository.BeneficiarioRepository;

@ExtendWith(MockitoExtension.class)
class BeneficiarioTest {

	@Mock
	private BeneficiarioRepository beneficiarioRepository;

	@Test
	void testSalvarBeneficiario() {
		// Cria um Beneficiario fictício
		Beneficiario beneficiario = new Beneficiario();
		beneficiario.setCpf("12345678901");
		beneficiario.setNome("João");
		beneficiario.setDataNascimento(LocalDate.of(1990, 5, 15));

		// Define o comportamento do BeneficiarioRepository mock para o método save
		Mockito.when(beneficiarioRepository.save(Mockito.any(Beneficiario.class))).thenReturn(beneficiario);

		// Chama o serviço ou método que salva um Beneficiario
		Beneficiario beneficiarioSalvo = beneficiarioRepository.save(beneficiario);

		// Verifiqua se o Beneficiario foi salvo corretamente
		assertNotNull(beneficiario);
		assertEquals("12345678901", beneficiario.getCpf());
		assertEquals("João", beneficiario.getNome());
		assertEquals(LocalDate.of(1990, 5, 15), beneficiario.getDataNascimento());
	}
	
	@Test
	void testToString() {
		Beneficiario beneficiario1 = new Beneficiario();
		beneficiario1.setCpf("12345678901");
		beneficiario1.setNome("João");
		beneficiario1.setDataNascimento(LocalDate.of(1990, 5, 15));
		
		beneficiario1.toString();
		
	}

}

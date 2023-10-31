package br.com.keyrus.beneficiarios.beneficiario.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import br.com.keyrus.beneficiarios.dto.BeneficiarioDTO;

class BeneficiarioDTOTest {

	@Test
	void testConstrutores() {
		String cpf = "12345678900";
		String nome = "João da Silva";
		String dataNascimento = "1990-01-15";

		BeneficiarioDTO beneficiario = new BeneficiarioDTO(cpf, nome, dataNascimento);

		assertNotNull(beneficiario);
		assertEquals(cpf, beneficiario.getCpf());
		assertEquals(nome, beneficiario.getNome());
		assertEquals(dataNascimento, beneficiario.getDataNascimento());
	}

	@Test
	void testEqualsAndHashCode() {
		BeneficiarioDTO beneficiario1 = new BeneficiarioDTO("12345678900", "João da Silva", "1990-01-15");
		BeneficiarioDTO beneficiario2 = new BeneficiarioDTO("12345678900", "João da Silva", "1990-01-15");

		assertEquals(beneficiario1, beneficiario2);
		assertEquals(beneficiario1.hashCode(), beneficiario2.hashCode());
	}

	@Test
	void testToString() {
		BeneficiarioDTO beneficiario = new BeneficiarioDTO("98765432100", "Maria Santos", "1985-05-20");
		String expectedToString = "BeneficiarioDTO(cpf=98765432100, nome=Maria Santos, dataNascimento=1985-05-20)";

		assertEquals(expectedToString, beneficiario.toString());
	}

}

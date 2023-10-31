package br.com.keyrus.beneficiarios.especialidade.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import br.com.keyrus.beneficiarios.domain.Especialidade;

class EspecialidadeTest {

	@Test
	void testConstrutor() {
		String nomeEspecialidade = "Cardiologia";
		Especialidade especialidade = new Especialidade(nomeEspecialidade);

		assertNotNull(especialidade);
		assertNull(especialidade.getCodigoEspecialidade()); // O campo código não foi definido no construtor
		assertEquals(nomeEspecialidade, especialidade.getNomeEspecialidade());
	}

	@Test
	void testEqualsAndHashCode() {
		Especialidade especialidade1 = new Especialidade("Cardiologia");
		Especialidade especialidade2 = new Especialidade("Cardiologia");

		assertEquals(especialidade1, especialidade2);
		assertEquals(especialidade1.hashCode(), especialidade2.hashCode());
	}

	@Test
	void testToString() {
		Especialidade especialidade = new Especialidade("Ortopedia");
		String expectedToString = "Especialidade(codigoEspecialidade=null, nomeEspecialidade=Ortopedia)";

		assertEquals(expectedToString, especialidade.toString());
	}

}

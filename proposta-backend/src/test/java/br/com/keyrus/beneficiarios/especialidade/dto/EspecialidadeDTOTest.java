package br.com.keyrus.beneficiarios.especialidade.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import br.com.keyrus.beneficiarios.dto.EspecialidadeDTO;

class EspecialidadeDTOTest {

	@Test
	void testConstrutor() {
		String nomeEspecialidade = "Cardiologia";
		EspecialidadeDTO especialidadeDTO = new EspecialidadeDTO(nomeEspecialidade);

		assertNotNull(especialidadeDTO);
		assertNull(especialidadeDTO.getCodigoEspecialidade()); // O campo código não foi definido no construtor
		assertEquals(nomeEspecialidade, especialidadeDTO.getNomeEspecialidade());
	}

	@Test
	void testEqualsAndHashCode() {
		EspecialidadeDTO especialidadeDTO1 = new EspecialidadeDTO("Cardiologia");
		EspecialidadeDTO especialidadeDTO2 = new EspecialidadeDTO("Cardiologia");

		assertEquals(especialidadeDTO1, especialidadeDTO2);
		assertEquals(especialidadeDTO1.hashCode(), especialidadeDTO2.hashCode());
	}

	@Test
	void testToString() {
		EspecialidadeDTO especialidadeDTO = new EspecialidadeDTO("Ortopedia");
		String expectedToString = "EspecialidadeDTO(codigoEspecialidade=null, nomeEspecialidade=Ortopedia)";

		assertEquals(expectedToString, especialidadeDTO.toString());
	}

}

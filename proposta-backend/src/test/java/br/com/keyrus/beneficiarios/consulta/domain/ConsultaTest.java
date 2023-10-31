package br.com.keyrus.beneficiarios.consulta.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import br.com.keyrus.beneficiarios.domain.Beneficiario;
import br.com.keyrus.beneficiarios.domain.Consulta;
import br.com.keyrus.beneficiarios.domain.Especialidade;

class ConsultaTest {

	@Test
	void testGettersAndSetters() {
		Consulta consulta = new Consulta();

		Especialidade especialidade = new Especialidade();
		Beneficiario beneficiario = new Beneficiario();
		LocalDateTime dataHora = LocalDateTime.of(2023, 10, 31, 15, 30);

		consulta.setEspecialidade(especialidade);
		consulta.setBeneficiario(beneficiario);
		consulta.setDataHora(dataHora);

		assertEquals(especialidade, consulta.getEspecialidade());
		assertEquals(beneficiario, consulta.getBeneficiario());
		assertEquals(dataHora, consulta.getDataHora());
	}

	@Test
	void testEqualsAndHashCode() {
		Consulta consulta1 = new Consulta();
		consulta1.setId(1);
		Consulta consulta2 = new Consulta();
		consulta2.setId(1);

		assertEquals(consulta1, consulta2);
		assertEquals(consulta1.hashCode(), consulta2.hashCode());
	}

	@Test
	void testToString() {
		Consulta consulta = new Consulta();

		Especialidade especialidade = new Especialidade();
		Beneficiario beneficiario = new Beneficiario();
		LocalDateTime dataHora = LocalDateTime.of(2023, 10, 31, 15, 30);

		consulta.setEspecialidade(especialidade);
		consulta.setBeneficiario(beneficiario);
		consulta.setDataHora(dataHora);
		consulta.toString();
	}

}

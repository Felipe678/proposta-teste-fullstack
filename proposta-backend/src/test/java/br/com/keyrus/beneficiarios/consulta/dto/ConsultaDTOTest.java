package br.com.keyrus.beneficiarios.consulta.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import br.com.keyrus.beneficiarios.dto.ConsultaDTO;

class ConsultaDTOTest {

    @Test
    void testGettersAndSetters() {
        // Crie uma instância de ConsultaDTO
        ConsultaDTO consultaDTO = new ConsultaDTO();

        // Defina valores para os campos
        consultaDTO.setId(1);
        consultaDTO.setEspecialidade("Cardiologia");
        consultaDTO.setBeneficiario("João");
        consultaDTO.setDataHora("2023-10-31 15:30:00");

        // Verifique se os valores podem ser recuperados corretamente
        assertEquals(1, consultaDTO.getId());
        assertEquals("Cardiologia", consultaDTO.getEspecialidade());
        assertEquals("João", consultaDTO.getBeneficiario());
        assertEquals("2023-10-31 15:30:00", consultaDTO.getDataHora());
    }
	
    @Test
    void testEquals() {
        ConsultaDTO consultaDTO1 = new ConsultaDTO(1, "Cardiologia", "João", "2023-10-31 15:30:00");
        ConsultaDTO consultaDTO2 = new ConsultaDTO(1, "Cardiologia", "João", "2023-10-31 15:30:00");

        assertEquals(consultaDTO1, consultaDTO2);
    }

    @Test
    void testHashCode() {
        ConsultaDTO consultaDTO1 = new ConsultaDTO(1, "Cardiologia", "João", "2023-10-31 15:30:00");
        ConsultaDTO consultaDTO2 = new ConsultaDTO(1, "Cardiologia", "João", "2023-10-31 15:30:00");

        assertEquals(consultaDTO1.hashCode(), consultaDTO2.hashCode());
    }

    @Test
    void testConstructor() {
        ConsultaDTO consultaDTO = new ConsultaDTO(1, "Cardiologia", "João", "2023-10-31 15:30:00");

        assertEquals(1, consultaDTO.getId());
        assertEquals("Cardiologia", consultaDTO.getEspecialidade());
        assertEquals("João", consultaDTO.getBeneficiario());
        assertEquals("2023-10-31 15:30:00", consultaDTO.getDataHora());
    }

    @Test
    void testToString() {
        ConsultaDTO consultaDTO = new ConsultaDTO(1, "Cardiologia", "João", "2023-10-31 15:30:00");

        assertEquals("ConsultaDTO(id=1, especialidade=Cardiologia, beneficiario=João, dataHora=2023-10-31 15:30:00)", consultaDTO.toString());
    }

}

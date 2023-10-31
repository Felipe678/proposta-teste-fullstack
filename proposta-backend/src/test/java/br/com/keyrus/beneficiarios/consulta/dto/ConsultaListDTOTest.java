package br.com.keyrus.beneficiarios.consulta.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import br.com.keyrus.beneficiarios.dto.ConsultaDTO;
import br.com.keyrus.beneficiarios.dto.ConsultaListDTO;

class ConsultaListDTOTest {

    @Test
    void testConstrutor() {
        String beneficiario = "12345678900";
        List<ConsultaDTO> consultas = new ArrayList<>();
        ConsultaListDTO consultaList = new ConsultaListDTO(beneficiario, consultas);

        assertNotNull(consultaList);
        assertEquals(beneficiario, consultaList.getBeneficiario());
        assertEquals(consultas, consultaList.getConsultas());
    }

    @Test
    void testEqualsAndHashCode() {
        List<ConsultaDTO> consultas1 = new ArrayList<>();
        List<ConsultaDTO> consultas2 = new ArrayList<>();
        consultas1.add(new ConsultaDTO());
        consultas2.add(new ConsultaDTO());

        ConsultaListDTO consultaList1 = new ConsultaListDTO("12345678900", consultas1);
        ConsultaListDTO consultaList2 = new ConsultaListDTO("12345678900", consultas2);

        assertEquals(consultaList1, consultaList2);
        assertEquals(consultaList1.hashCode(), consultaList2.hashCode());
    }

    @Test
    void testToString() {
        List<ConsultaDTO> consultas = new ArrayList<>();
        ConsultaListDTO consultaList = new ConsultaListDTO("98765432100", consultas);
        String expectedToString = "ConsultaListDTO(beneficiario=98765432100, consultas=[])";

        assertEquals(expectedToString, consultaList.toString());
    }
	
}

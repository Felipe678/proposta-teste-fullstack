package br.com.keyrus.beneficiarios.dto;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public @Data class ConsultaListDTO implements Serializable {

	private static final long serialVersionUID = -9091470573982451425L;

	private String beneficiario;
	
	private List<ConsultaDTO> consultas;
}

package br.com.keyrus.beneficiarios.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public @Data class ConsultaDTO implements Serializable {

	private static final long serialVersionUID = 5976873050432607584L;

	private Integer id;
	
	private String especialidade;

	private String beneficiario;
	
	private String dataHora;
	
}

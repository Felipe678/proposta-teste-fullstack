package br.com.keyrus.beneficiarios.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public @Data class EspecialidadeDTO implements Serializable{
	
	private static final long serialVersionUID = 6385940478870946591L;
	
	private Integer codigoEspecialidade;
    private String nomeEspecialidade;
	
    public EspecialidadeDTO(String nomeEspecialidade) {
		super();
		this.nomeEspecialidade = nomeEspecialidade;
	}

}

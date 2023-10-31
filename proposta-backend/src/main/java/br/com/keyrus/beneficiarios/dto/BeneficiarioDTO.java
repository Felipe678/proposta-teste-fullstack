package br.com.keyrus.beneficiarios.dto;

import java.io.Serializable;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public @Data class BeneficiarioDTO implements Serializable{

	private static final long serialVersionUID = -6070111411439398691L;

	private String cpf;
	
	private String nome;
	
	private String dataNascimento;
	
}

package br.com.keyrus.beneficiarios.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
public @Data class Especialidade {

	@Id
    @GeneratedValue
    private Integer codigoEspecialidade;
    private String nomeEspecialidade;
	
    public Especialidade(String nomeEspecialidade) {
		super();
		this.nomeEspecialidade = nomeEspecialidade;
	}

	public Especialidade() {
		super();
	}
	
    
}

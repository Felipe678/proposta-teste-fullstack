package br.com.keyrus.beneficiarios.domain;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;

@Entity
public @Data class Beneficiario {

	@Id
	private String cpf;
	private String nome;
	private LocalDate dataNascimento;
	
	
	
	public Beneficiario(String cpf, String nome, LocalDate dataNascimento) {
		super();
		this.cpf = cpf;
		this.nome = nome;
		this.dataNascimento = dataNascimento;
	}

	public Beneficiario() {
		super();
	}

}
package br.com.keyrus.beneficiarios.domain;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Data;

@Entity
public @Data class Consulta {

	@Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    private Especialidade especialidade;

    @ManyToOne
    private Beneficiario beneficiario;

    private LocalDateTime dataHora;

}

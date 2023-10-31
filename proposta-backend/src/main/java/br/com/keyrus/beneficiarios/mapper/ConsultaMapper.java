package br.com.keyrus.beneficiarios.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import br.com.keyrus.beneficiarios.domain.Consulta;
import br.com.keyrus.beneficiarios.dto.ConsultaDTO;

@Mapper(componentModel = "spring")
public abstract class ConsultaMapper {

	@Mapping(source = "beneficiario.cpf", target = "beneficiario")
	@Mapping(source = "especialidade.nomeEspecialidade", target = "especialidade")
	@Mapping(source = "dataHora" ,target = "dataHora", qualifiedByName = "dateToString")
	public abstract ConsultaDTO toDTO(Consulta consulta);
	
	@Mapping(source = "beneficiario", target = "beneficiario.cpf")
	@Mapping(source = "especialidade", target = "especialidade.nomeEspecialidade")
	@Mapping(source = "dataHora" ,target = "dataHora", qualifiedByName = "stringToDate")
	public abstract Consulta toEntity(ConsultaDTO consulta);
	
	@Named("dateToString")
	public String dateToString(LocalDateTime date) {
		if(Objects.isNull(date))
			return null;
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return date.format(formatter);
	}
	
	@Named("stringToDate")
	public LocalDateTime stringToDate(String date) {
		if(Objects.isNull(date))
			return null;
		
		return LocalDateTime.parse(date);
	}
	
}

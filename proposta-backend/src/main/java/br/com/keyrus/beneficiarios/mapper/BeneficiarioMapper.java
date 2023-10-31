package br.com.keyrus.beneficiarios.mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import br.com.keyrus.beneficiarios.domain.Beneficiario;
import br.com.keyrus.beneficiarios.dto.BeneficiarioDTO;

@Mapper(componentModel = "spring")
public abstract class BeneficiarioMapper {

	@Mapping(source = "dataNascimento", target = "dataNascimento", qualifiedByName = "dateToString")
	public abstract BeneficiarioDTO toDTO(Beneficiario beneficiario);

	@Mapping(source = "dataNascimento", target = "dataNascimento", qualifiedByName = "toDate")
	public abstract Beneficiario toEntity(BeneficiarioDTO beneficiario);

	@Named("dateToString")
	public String dateToString(LocalDate date) {
		if(Objects.isNull(date))
			return null;
		
		return date.toString();
	}
	
	@Named("toDate")
	public LocalDate dateToString(String date) {
		if(Objects.isNull(date))
			return null;
		
		return LocalDate.parse(date);
	}
	
	
}

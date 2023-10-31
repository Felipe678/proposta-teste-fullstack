package br.com.keyrus.beneficiarios.mapper;

import org.mapstruct.Mapper;

import br.com.keyrus.beneficiarios.domain.Especialidade;
import br.com.keyrus.beneficiarios.dto.EspecialidadeDTO;

@Mapper(componentModel = "spring")
public interface EspecialidadeMapper {

	public EspecialidadeDTO toDTO(Especialidade especialidade);
	
	public Especialidade toEntity(EspecialidadeDTO especialidadeDTO);
	
}

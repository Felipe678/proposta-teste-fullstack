package br.com.keyrus.beneficiarios.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.keyrus.beneficiarios.domain.Especialidade;
import br.com.keyrus.beneficiarios.dto.EspecialidadeDTO;
import br.com.keyrus.beneficiarios.exceptions.EspecialidadeException;
import br.com.keyrus.beneficiarios.mapper.EspecialidadeMapper;
import br.com.keyrus.beneficiarios.repository.EspecialidadeRepository;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class EspecialidadeService {

	@Autowired
	private EspecialidadeRepository repository;
	
	@Autowired
	private EspecialidadeMapper mapper;
	
	/**
	 * 
	 * @param String nomeEspecialidade
	 * @return Especialidade
	 * 
	 * author: felipe.nogueira
	 * created: 30/10/2023
	 * @throws EspecialidadeException 
	 */
	public Especialidade buscarEspecialidadePorNome(String nomeEspecialidade) throws EspecialidadeException {
		Especialidade response = new Especialidade();
		try {
			Optional<Especialidade> especialidade = repository.findEspecialidadeByNomeEspecialidade(nomeEspecialidade);
			
			validarEspecialidade(especialidade);
			
			response = especialidade.get();
		} catch (Exception e) {
			log.error("Erro na linha: "+ e.getStackTrace()[0].getLineNumber());
			log.info("erro ao buscar especialidade: "+e.getMessage());
			throw new EspecialidadeException("Especialidade não encontrada");
		}
		return response;
	}

	private void validarEspecialidade(Optional<Especialidade> especialidade) throws EspecialidadeException {
		if(especialidade.isEmpty())
			throw new EspecialidadeException("Especialidade não encontrada");
	}
	
	/**
	 * 
	 * @return List<EspecialidadeDTO>
	 * 
	 * author: felipe.nogueira
	 * created: 30/10/2023
	 * @throws EspecialidadeException 
	 */
	public List<EspecialidadeDTO> listarTodasEspecialidades() throws EspecialidadeException {
		List<EspecialidadeDTO> response = new ArrayList<>();
		try {
			repository.findAll().forEach(e -> response.add(mapper.toDTO(e)));
			
		} catch (Exception e) {
			log.error("Erro na linha: "+ e.getStackTrace()[0].getLineNumber());
			log.info("erro ao buscar especialidade: "+e.getMessage());
			throw new EspecialidadeException("Especialidades não encontradas");
		}
		return response;
	}
	
}

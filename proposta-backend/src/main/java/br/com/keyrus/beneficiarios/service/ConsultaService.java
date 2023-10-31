package br.com.keyrus.beneficiarios.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.keyrus.beneficiarios.domain.Consulta;
import br.com.keyrus.beneficiarios.dto.BeneficiarioDTO;
import br.com.keyrus.beneficiarios.dto.ConsultaDTO;
import br.com.keyrus.beneficiarios.dto.ConsultaListDTO;
import br.com.keyrus.beneficiarios.exceptions.BeneficiarioNaoCadastradoException;
import br.com.keyrus.beneficiarios.exceptions.ConflitoAgendamentoException;
import br.com.keyrus.beneficiarios.exceptions.EspecialidadeException;
import br.com.keyrus.beneficiarios.mapper.BeneficiarioMapper;
import br.com.keyrus.beneficiarios.mapper.ConsultaMapper;
import br.com.keyrus.beneficiarios.repository.ConsultaRepository;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ConsultaService {

	public static final String ERRO_NA_LINHA = "Erro na linha: ";
	
	@Autowired
	private ConsultaRepository repository;
	
	@Autowired
	private BeneficiarioService beneficiarioService;
	
	@Autowired
	private BeneficiarioMapper beneficiarioMapper;
	
	@Autowired
	private EspecialidadeService especialidadeService;
	
	@Autowired
	private ConsultaMapper mapper;
	
	/**
	 * 
	 * @param ConsultaDTO
	 * @return ConsultaDTO
	 * 
	 * author: felipe.nogueira
	 * created: 30/10/2023
	 * @throws ConflitoAgendamentoException 
	 */
	public ConsultaDTO cadastrarConsulta(ConsultaDTO consultaDTO) throws ConflitoAgendamentoException {
		ConsultaDTO response = new ConsultaDTO();
		try {
			Consulta consulta = mapper.toEntity(consultaDTO);
			
			//Valida se já existe a consulta enviada
			validarAgendamento(consultaDTO.getBeneficiario(), consulta.getDataHora());
			
			//Valida e preenche a especialidade
			preencherEspecialidade(consultaDTO.getEspecialidade(), consulta);
			
			//Valida e preenche o beneficiário
			preencherBeneficiario(consultaDTO.getBeneficiario(), consulta);
			
			repository.save(consulta);
			
		} catch (Exception e) {
			log.error(ERRO_NA_LINHA+ e.getStackTrace()[0].getLineNumber());
			log.info("erro ao cadastrar consulta: "+e.getMessage());
			throw new ConflitoAgendamentoException(e.getMessage());
		}
		return response;
	}
	
	public void validarAgendamento(String beneficiario, LocalDateTime dataHora) throws ConflitoAgendamentoException {
		if(repository.getConsultaByBeneficiarioCpfAndDataHora(beneficiario, dataHora).isPresent())
			throw new ConflitoAgendamentoException("Conflito de Agendas. Foi encontrado outro agendamento para o mesmo dia e horario.");
		
	}
	
	/**
	 * 
	 * @param nomeEspecialidade
	 * @param consulta
	 * 
	 * author: felipe.nogueira
	 * created: 30/10/2023
	 * @throws EspecialidadeException 
	 */
	public void preencherEspecialidade(String nomeEspecialidade, Consulta consulta) throws EspecialidadeException {
		consulta.setEspecialidade(especialidadeService.buscarEspecialidadePorNome(nomeEspecialidade));
	}
	
	/**
	 * 
	 * @param cpfBeneficiario
	 * @param consulta
	 * 
	 * author: felipe.nogueira
	 * created: 30/10/2023 
	 * @throws BeneficiarioNaoCadastradoException 
	 */
	public void preencherBeneficiario(String cpfBeneficiario, Consulta consulta) throws BeneficiarioNaoCadastradoException {
		BeneficiarioDTO beneficiario = beneficiarioService.consultarBeneficiario(cpfBeneficiario);
		consulta.setBeneficiario(beneficiarioMapper.toEntity(beneficiario));
	}
	
	
	/**
	 * 
	 * @param cpfBeneficiario
	 * @return
	 * 
	 * author: felipe.nogueira
	 * created: 30/10/2023
	 */
	public ConsultaListDTO listarConsultas(String cpfBeneficiario) {
		ConsultaListDTO response = new ConsultaListDTO();
		List<ConsultaDTO> consultas = new ArrayList<>();
		try {
			response.setBeneficiario(cpfBeneficiario);
			repository.getConsultasByBeneficiarioCpf(cpfBeneficiario).forEach(consulta -> 
				consultas.add(mapper.toDTO(consulta))
			);
			response.setConsultas(consultas);
		} catch (Exception e) {
			log.error(ERRO_NA_LINHA+ e.getStackTrace()[0].getLineNumber());
			log.info("erro ao listar consultas do beneficiario: "+e.getMessage());
		}
		return response;
	}
	
	/**
	 * 
	 * @param idConsulta
	 * 
	 * author: felipe.nogueira
	 * created: 30/10/2023
	 */
	public void excluirConsulta(Integer idConsulta) {
		try {
			repository.deleteById(idConsulta);
		} catch (Exception e) {
			log.error(ERRO_NA_LINHA+ e.getStackTrace()[0].getLineNumber());
			log.info("erro ao excluir consulta: "+e.getMessage());
		}
	}
	
	/**
	 * 
	 * author: felipe.nogueira
	 * created: 30/10/2023
	 */
	public void limparBase() {
		try {
			repository.deleteAll();
		} catch (Exception e) {
			log.error(ERRO_NA_LINHA+ e.getStackTrace()[0].getLineNumber());
			log.info("erro ao excluir consulta: "+e.getMessage());
		}
	}
	
}

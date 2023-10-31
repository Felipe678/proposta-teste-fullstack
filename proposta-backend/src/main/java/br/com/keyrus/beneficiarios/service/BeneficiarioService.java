package br.com.keyrus.beneficiarios.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.keyrus.beneficiarios.domain.Beneficiario;
import br.com.keyrus.beneficiarios.dto.BeneficiarioDTO;
import br.com.keyrus.beneficiarios.exceptions.BeneficiarioCadastradoException;
import br.com.keyrus.beneficiarios.exceptions.BeneficiarioNaoCadastradoException;
import br.com.keyrus.beneficiarios.mapper.BeneficiarioMapper;
import br.com.keyrus.beneficiarios.repository.BeneficiarioRepository;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class BeneficiarioService {

	public static final String ERRO_NA_LINHA = "Erro na linha: ";
	
	@Autowired
	private BeneficiarioRepository repository;
	
	@Autowired
	private BeneficiarioMapper mapper;
	
	/**
	 * Método responsável por cadastrar um beneficiario na base de dados
	 * 
	 * @param beneficiarioDTO
	 * @return
	 * 
	 * author: felipe.nogueira
	 * created: 30/10/2023
	 * @throws BeneficiarioCadastradoException 
	 */
	public BeneficiarioDTO cadastrarBeneficiario(BeneficiarioDTO beneficiarioDTO) throws BeneficiarioCadastradoException {
		BeneficiarioDTO response = new BeneficiarioDTO();
		try {
			Beneficiario beneficiario = mapper.toEntity(beneficiarioDTO);

			validarBeneficiario(beneficiario.getCpf());

			response = mapper.toDTO(repository.save(beneficiario));
			
		} catch (Exception e) {
			log.error(ERRO_NA_LINHA+ e.getStackTrace()[0].getLineNumber());
			log.info("erro ao cadastrar beneficiário: "+e.getMessage());
			throw new BeneficiarioCadastradoException("Beneficiario já cadastrado");
		}
		
		return response;
	}
	
	/**
	 * Método responsável por validar se o beneficiario já está cadastrado
	 * 
	 * @param cpf
	 * @throws BeneficiarioCadastradoException
	 * 
	 * author: felipe.nogueira
	 * created: 30/10/2023
	 */
	public void validarBeneficiario(String cpf) throws BeneficiarioCadastradoException {
		if(repository.getBeneficiarioByCpf(cpf).isPresent()) {
			throw new BeneficiarioCadastradoException("Beneficiario já cadastrado");
		}
	}
	
	/**
	 * Método responsável por realizar a consulta do beneficiario na base de dados
	 * 
	 * @param cpf
	 * @return
	 * 
	 * author: felipe.nogueira
	 * created: 30/10/2023
	 * @throws BeneficiarioNaoCadastradoException 
	 */
	public BeneficiarioDTO consultarBeneficiario(String cpf) throws BeneficiarioNaoCadastradoException {
		BeneficiarioDTO response = new BeneficiarioDTO();
		try {
			Optional<Beneficiario> beneficiario = repository.getBeneficiarioByCpf(cpf);
			if(beneficiario.isEmpty())
				throw new BeneficiarioNaoCadastradoException("Beneficiario não cadastrado");
			
			response = mapper.toDTO(beneficiario.get());
			
		} catch (Exception e) {
			log.error(ERRO_NA_LINHA+ e.getStackTrace()[0].getLineNumber());
			log.info("erro ao consultar beneficiário: "+e.getMessage());
			throw new BeneficiarioNaoCadastradoException("Beneficiario não cadastrado");
		}
		
		return response;
	}
	
	/**
	 * Método responsável por realizar a exclusão do beneficiario na base de dados
	 * 
	 * @param cpf
	 * 
	 * author: felipe.nogueira
	 * created: 30/10/2023
	 * @throws BeneficiarioNaoCadastradoException 
	 */
	public void excluirBeneficiario(String cpf) throws BeneficiarioNaoCadastradoException {
		try {
			repository.deleteById(cpf);
		} catch (Exception e) {
			log.error(ERRO_NA_LINHA+ e.getStackTrace()[0].getLineNumber());
			log.info("erro ao excluir beneficiário: "+e.getMessage());
			throw new BeneficiarioNaoCadastradoException("Beneficiario não cadastrado");
		}
	}
	
	/**
	 * Método responsável por realizar a limpeza total na base de Beneficiarios
	 * 
	 * author: felipe.nogueira
	 * created: 30/10/2023
	 * @throws BeneficiarioNaoCadastradoException 
	 */
	public void limparBase() throws BeneficiarioNaoCadastradoException {
		try {
			repository.deleteAll();
		} catch (Exception e) {
			log.error(ERRO_NA_LINHA+ e.getStackTrace()[0].getLineNumber());
			log.info("erro ao excluir beneficiários: "+e.getMessage());
			throw new BeneficiarioNaoCadastradoException("Beneficiario não cadastrado");
		}
	}
	
}

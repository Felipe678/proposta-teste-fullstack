package br.com.keyrus.beneficiarios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.keyrus.beneficiarios.dto.BeneficiarioDTO;
import br.com.keyrus.beneficiarios.exceptions.BeneficiarioNaoCadastradoException;
import br.com.keyrus.beneficiarios.service.BeneficiarioService;
import br.com.keyrus.beneficiarios.service.ConsultaService;

@RestController
@RequestMapping
public class ResetController {

	@Autowired
	private BeneficiarioService beneficiarioService;
	
	@Autowired
	private ConsultaService consultaService;
	
	@PostMapping(value = "/reset")
	public <T> ResponseEntity<T> excluirBeneficariosEConsultas() throws BeneficiarioNaoCadastradoException{
		
		//Limpa a base completa de consultas
		consultaService.limparBase();

		//Limpa a base completa de beneficiários
		beneficiarioService.limparBase();
		
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}

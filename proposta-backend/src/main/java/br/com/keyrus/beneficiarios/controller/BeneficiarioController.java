package br.com.keyrus.beneficiarios.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.keyrus.beneficiarios.dto.BeneficiarioDTO;
import br.com.keyrus.beneficiarios.exceptions.CpfException;
import br.com.keyrus.beneficiarios.service.BeneficiarioService;

@RestController
@RequestMapping(value = "/beneficiarios")
public class BeneficiarioController {

	@Autowired
	private BeneficiarioService service;
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public <T> ResponseEntity<T> cadastrarBeneficiario(@RequestBody BeneficiarioDTO beneficiario){
		try {
			service.cadastrarBeneficiario(beneficiario);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping
	public ResponseEntity<Object> consultarBeneficiario(@RequestParam String cpfBeneficiario) {
		try {
			validarCpf(cpfBeneficiario);
			return new ResponseEntity<>(service.consultarBeneficiario(cpfBeneficiario), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping
	public <T> ResponseEntity<T> deletarBeneficiario(@RequestParam String cpfBeneficiario){
		try {
			validarCpf(cpfBeneficiario);
			service.excluirBeneficiario(cpfBeneficiario);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	public void validarCpf(String cpf) throws CpfException {
		if(Objects.isNull(cpf) || cpf.length() != 11)
			throw new CpfException("CPF Inválido");
	}
}

package br.com.keyrus.beneficiarios.controller;

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

import br.com.keyrus.beneficiarios.dto.ConsultaDTO;
import br.com.keyrus.beneficiarios.dto.ConsultaListDTO;
import br.com.keyrus.beneficiarios.service.ConsultaService;

@RestController
@RequestMapping(value = "/consultas")
public class ConsultaController {

	@Autowired
	private ConsultaService service;
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> cadastrarConsulta(@RequestBody ConsultaDTO consulta){
		try {
			service.cadastrarConsulta(consulta);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
		}
	}
	
	@GetMapping
	public ResponseEntity<ConsultaListDTO> listarConsultas(@RequestParam String cpfBeneficiario){
		ConsultaListDTO response = service.listarConsultas(cpfBeneficiario);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@DeleteMapping
	public <T> ResponseEntity<T> excluirConsulta(@RequestParam Integer idConsulta){
		service.excluirConsulta(idConsulta);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}

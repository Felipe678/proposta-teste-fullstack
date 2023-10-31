package br.com.keyrus.beneficiarios.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.keyrus.beneficiarios.dto.EspecialidadeDTO;
import br.com.keyrus.beneficiarios.exceptions.EspecialidadeException;
import br.com.keyrus.beneficiarios.service.EspecialidadeService;

@RestController
@RequestMapping(value = "/especialidades")
public class EspecialidadeController {

	@Autowired
	private EspecialidadeService service;
	
	@GetMapping
	public ResponseEntity<List<EspecialidadeDTO>> listarEspecialidades() throws EspecialidadeException{
		List<EspecialidadeDTO> response = service.listarTodasEspecialidades();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
}

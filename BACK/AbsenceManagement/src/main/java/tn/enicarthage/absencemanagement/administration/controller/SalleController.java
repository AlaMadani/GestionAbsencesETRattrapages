package tn.enicarthage.absencemanagement.administration.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import tn.enicarthage.absencemanagement.administration.model.Salle;
import tn.enicarthage.absencemanagement.administration.repository.SallesRepository;
import tn.enicarthage.absencemanagement.administration.service.SalleService;
import tn.enicarthage.absencemanagement.administration.service.SeanceDispoENSERVICE;

@RestController
@RequestMapping("/api/Salles")
@AllArgsConstructor
public class SalleController {
	@Autowired
	private SalleService service;
	@GetMapping
	public List<String> getALL(){
		return service.getALL();
	}

}

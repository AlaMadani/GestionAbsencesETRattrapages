package tn.enicarthage.absencemanagement.administration.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import tn.enicarthage.absencemanagement.administration.model.EnseignantDTO;

import tn.enicarthage.absencemanagement.administration.service.EnseignantService;
import tn.enicarthage.absencemanagement.enseignants.model.Enseignant;


@AllArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class EnseignantController {
	@Autowired
 private final EnseignantService enseignantService;
	 
	 @GetMapping("/enseignants")
	    public List<EnseignantDTO> getAllStudents() {
	        return enseignantService.getAllEnseignants().stream()
	        		.map(e->new EnseignantDTO(e.getId(),e.getNom(),e.getPrenom(),e.getEmail(),e.getMotdepass(),e.getGrade(),e.getNum_tel(),e.getNbAbsences())).toList();
	    }
}

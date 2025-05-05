package tn.enicarthage.absencemanagement.administration.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import tn.enicarthage.absencemanagement.administration.model.AbsenceDTO;
import tn.enicarthage.absencemanagement.administration.model.SeanceDTO;
import tn.enicarthage.absencemanagement.administration.model.SeanceInfo;
import tn.enicarthage.absencemanagement.administration.service.AbsenceService;
import tn.enicarthage.absencemanagement.administration.service.SeanceService;
import tn.enicarthage.absencemanagement.enseignants.model.Aceptee;
import tn.enicarthage.absencemanagement.etudiants.model.Classe;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class SeanceController {
	@Autowired
	 private final SeanceService seanceservice ;
		 @GetMapping("/IsRattrapage")
		    public  SeanceInfo IsRattrapage(@RequestParam int id) {
		        return seanceservice.IsRattrappage(id);
		 }
		 
		 
		 @GetMapping("/seance/{id}")
		    public SeanceDTO getSeanceById(@PathVariable int id) {
		        return seanceservice.getSeanceById(id);
		    }

}

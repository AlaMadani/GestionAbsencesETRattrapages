package tn.enicarthage.absencemanagement.administration.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.AllArgsConstructor;
import tn.enicarthage.absencemanagement.administration.model.EmploiTempsRowsDTO;
import tn.enicarthage.absencemanagement.administration.repository.SallesRepository;
import tn.enicarthage.absencemanagement.enseignants.model.Aceptee;
import tn.enicarthage.absencemanagement.enseignants.model.numSeance;

@AllArgsConstructor
@Service
public class SallesDispoService {
	@Autowired
	private final EmploiTempsService service;
	@Autowired
	private final SeanceService seanceservice;
	@Autowired
	private final RattrapageService ratservice;
	@Autowired
	private final SalleService sallerepo;
	
	public List<String>  getSallesDispo(
		       
	        @RequestParam 
	        @DateTimeFormat(pattern = "dd/MM/yyyy")
	        LocalDate dateS,
	        @RequestParam DayOfWeek day,
	        @RequestParam numSeance numS
	        //il faut ajouter 
	    ) {
		 List<EmploiTempsRowsDTO> emploi= service.getAllRows() .stream()
	                .filter(e->e.getJourr().toDayOfWeek().equals(day)).toList();
		 
		 List<String> salles=sallerepo.getALL();

for (EmploiTempsRowsDTO ed : emploi) {
	
Integer ids1=ed.getSeance1();
if (ids1 != null && numS==numSeance.S1) {
	if (seanceservice.IsRattrappage(ids1).getIsRattrapage()==Aceptee.oui) {
		if(ratservice.getDateAffRatt(ids1).equals(dateS)) {
		      salles.remove(seanceservice.IsRattrappage(ids1).getSalle());
		}
	}
	else { //elimine cette salle
		salles.remove(seanceservice.IsRattrappage(ids1).getSalle());
	}
}
Integer ids2=ed.getSeance2();
if (ids2 != null && numS==numSeance.S2) {
	if (seanceservice.IsRattrappage(ids2).getIsRattrapage()==Aceptee.oui) {
		if(ratservice.getDateAffRatt(ids2).equals(dateS)) {
		      salles.remove(seanceservice.IsRattrappage(ids2).getSalle());
		}
	}
	else { //elimine cette salle
		salles.remove(seanceservice.IsRattrappage(ids2).getSalle());
	}
}

Integer ids3=ed.getSeance3();
if (ids3 != null && numS==numSeance.S3) {
	if (seanceservice.IsRattrappage(ids3).getIsRattrapage()==Aceptee.oui) {
		if(ratservice.getDateAffRatt(ids3).equals(dateS)) {
		      salles.remove(seanceservice.IsRattrappage(ids3).getSalle());
		}
	}
	else { //elimine cette salle
		salles.remove(seanceservice.IsRattrappage(ids3).getSalle());
	}
}

Integer ids4=ed.getSeance4();
if (ids4 != null && numS==numSeance.S4) {
	if (seanceservice.IsRattrappage(ids4).getIsRattrapage()==Aceptee.oui) {
		if(ratservice.getDateAffRatt(ids4).equals(dateS)) {
		      salles.remove(seanceservice.IsRattrappage(ids4).getSalle());
		}
	}
	else { //elimine cette salle
		salles.remove(seanceservice.IsRattrappage(ids4).getSalle());
	}
}

Integer ids5=ed.getSeance5();
if (ids5 != null && numS==numSeance.S5) {
	if (seanceservice.IsRattrappage(ids5).getIsRattrapage()==Aceptee.oui) {
		if(ratservice.getDateAffRatt(ids5).equals(dateS)) {
		      salles.remove(seanceservice.IsRattrappage(ids5).getSalle());
		}
	}
	else { //elimine cette salle
		salles.remove(seanceservice.IsRattrappage(ids5).getSalle());
	}
}










 
		 
		 		 
} 
return salles;

}
}

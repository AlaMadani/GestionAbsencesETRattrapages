package tn.enicarthage.absencemanagement.administration.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import tn.enicarthage.absencemanagement.administration.model.SeanceInfo;
import tn.enicarthage.absencemanagement.administration.service.SallesDispoService;
import tn.enicarthage.absencemanagement.administration.service.SeanceService;
import tn.enicarthage.absencemanagement.enseignants.model.numSeance;

@AllArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class SallesDispo {
	@Autowired
	 private final SallesDispoService salesdispo;
	@GetMapping("/SallesDispo")
    public  List<String> getSallesDispo( 
    		@RequestParam 
	        @DateTimeFormat(pattern = "dd/MM/yyyy")
	        LocalDate dateS,
	        @RequestParam DayOfWeek day,
	        @RequestParam numSeance numS) {
        return  salesdispo.getSallesDispo(dateS,day,numS);
 }

}

package tn.enicarthage.absencemanagement.administration.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import java.util.HashMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import tn.enicarthage.absencemanagement.administration.service.SeanceDispoENSERVICE;
import tn.enicarthage.absencemanagement.administration.service.SeanceDispoETSERVICE;
import tn.enicarthage.absencemanagement.etudiants.model.Classe;
import tn.enicarthage.absencemanagement.etudiants.model.Groupe;
import tn.enicarthage.absencemanagement.etudiants.model.Specialite;
@RestController
@RequestMapping("/api/admin/SeancesDispoST_ET")
@AllArgsConstructor
public class SeanceDispoForST_ET {
	@Autowired
	private SeanceDispoENSERVICE seancedispoens;
	@Autowired 
	private SeanceDispoETSERVICE seancedispoetd;
	@GetMapping
	public Map<DayOfWeek, Map<Integer, List<LocalDate>>> getDispoResult(
			 @RequestParam Classe classe,
		        @RequestParam Specialite specialite,
		        @RequestParam Groupe groupe,
		        @RequestParam 
		        @DateTimeFormat(pattern = "dd/MM/yyyy")
		        LocalDate start,
		        @RequestParam 
		        @DateTimeFormat(pattern = "dd/MM/yyyy")
		        LocalDate end,
		        @RequestParam Long idens){
		
		Map<DayOfWeek, Map<Integer, List<LocalDate>>> dispoST=seancedispoetd.fetchTimetable(classe, specialite, groupe, start, end);
		Map<DayOfWeek, Map<Integer, List<LocalDate>>> completENS=seancedispoens.fetchTimetable(start, end, idens);
		Map<DayOfWeek, Map<Integer, List<LocalDate>>> possibles = new EnumMap<>(DayOfWeek.class);
		for (Map.Entry<DayOfWeek, Map<Integer, List<LocalDate>>> entry : dispoST.entrySet()) {
		    DayOfWeek jour = entry.getKey();
		    Map<Integer, List<LocalDate>> etuMap    = entry.getValue();
		    Map<Integer, List<LocalDate>> enseiMap  = completENS.getOrDefault(jour, Collections.emptyMap());
		    Map<Integer, List<LocalDate>> libreMap  = new HashMap<>();

		  
		    for (Map.Entry<Integer, List<LocalDate>> seanceEntry : etuMap.entrySet()) {
		        Integer noSeance   = seanceEntry.getKey();
		        List<LocalDate> etuDates   = seanceEntry.getValue();
		        List<LocalDate> ensDates   = enseiMap.getOrDefault(noSeance, Collections.emptyList());

		        
		        List<LocalDate> datesLibres = etuDates.stream()
		            .filter(d -> !ensDates.contains(d))
		            .collect(Collectors.toList());

		      
		        if (!datesLibres.isEmpty()) {
		            libreMap.put(noSeance, datesLibres);
		        }
		    }

		   
		    if (!libreMap.isEmpty()) {
		        possibles.put(jour, libreMap);
		    }
		}
		
		return possibles;
		
	}
	
	

}

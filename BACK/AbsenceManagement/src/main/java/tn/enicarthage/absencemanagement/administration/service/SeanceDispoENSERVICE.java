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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.AllArgsConstructor;
import tn.enicarthage.absencemanagement.administration.model.EmploiTempsRowsDTO;
import tn.enicarthage.absencemanagement.administration.repository.SeanceRepository;
import tn.enicarthage.absencemanagement.enseignants.model.Aceptee;

@AllArgsConstructor
@Service
public class SeanceDispoENSERVICE {
	@Autowired
	private final EmploiTempsService service;
	@Autowired
	private final SeanceService seanceservice;
	@Autowired
	private final RattrapageService ratservice;
	
	    public Map<DayOfWeek, Map<Integer, List<LocalDate>>>  fetchTimetable(
	       
	        @RequestParam 
	        @DateTimeFormat(pattern = "dd/MM/yyyy")
	        LocalDate start,
	        @RequestParam 
	        @DateTimeFormat(pattern = "dd/MM/yyyy")
	        LocalDate end,
	        @RequestParam Long idens
	        //il faut ajouter 
	    ) {
		 List<EmploiTempsRowsDTO> emploi= service.getAllRows() .stream()
	                .sorted(Comparator.comparing(EmploiTempsRowsDTO::getJourr))
	                .collect(Collectors.toList());
		 
		
	
	
		 List<LocalDate> allDates = Stream.iterate(start, d -> d.plusDays(1))
              .limit(start.until(end).getDays() + 1)
              .collect(Collectors.toList());

//5) Construire la map finale
Map<DayOfWeek, Map<Integer, List<LocalDate>>> result = new EnumMap<>(DayOfWeek.class);

for (EmploiTempsRowsDTO ed : emploi) {
	
DayOfWeek dow = ed.getJourr().toDayOfWeek(); // assumes JSON jourr = "LUNDI", etc.

//Extraire les dates du même jour de semaine
List<LocalDate> matchingDates = allDates.stream()
.filter(d -> d.getDayOfWeek() == dow)
.collect(Collectors.toList());

//Trouver les créneaux libres (null) et y associer la liste des dates BUSY BY SEANCE
Map<Integer, List<LocalDate>> freeBySeance = new HashMap<>();
if (ed.getSeance1() != null) {
if(seanceservice.IsRattrappage(ed.getSeance1()).getIdens()==idens){
	if (seanceservice.IsRattrappage(ed.getSeance1()).getIsRattrapage()==Aceptee.oui) {
		if(matchingDates.contains(ratservice.getDateAffRatt(ed.getSeance1()))) {
			LocalDate unwanted = ratservice.getDateAffRatt(ed.getSeance1());
			List<LocalDate> onlyUnwanted = matchingDates.stream()
				    .filter(d -> d.equals(unwanted))
				    .collect(Collectors.toList());
			freeBySeance.put(1, onlyUnwanted);
		}//BUSY BY SEANCE ALLL
		//else freeBySeance.put(1, matchingDates);
		
	}
	else {freeBySeance.put(1, matchingDates);}
	
}
}
if (ed.getSeance2() != null) {
if(seanceservice.IsRattrappage(ed.getSeance2()).getIdens()==idens){
	if (seanceservice.IsRattrappage(ed.getSeance2()).getIsRattrapage()==Aceptee.oui) {
		if(matchingDates.contains(ratservice.getDateAffRatt(ed.getSeance2()))) {
			LocalDate unwanted = ratservice.getDateAffRatt(ed.getSeance2());
			List<LocalDate> onlyUnwanted = matchingDates.stream()
				    .filter(d -> d.equals(unwanted))
				    .collect(Collectors.toList());
			freeBySeance.put(2, onlyUnwanted);
		}//BUSY BY SEANCE ALLL
		
		
	}
	else {freeBySeance.put(2, matchingDates);}
	
}
}
if (ed.getSeance3() != null) {
if(seanceservice.IsRattrappage(ed.getSeance3()).getIdens()==idens){
	if (seanceservice.IsRattrappage(ed.getSeance3()).getIsRattrapage()==Aceptee.oui) {
		if(matchingDates.contains(ratservice.getDateAffRatt(ed.getSeance3()))) {
			LocalDate unwanted = ratservice.getDateAffRatt(ed.getSeance3());
			List<LocalDate> onlyUnwanted = matchingDates.stream()
				    .filter(d -> d.equals(unwanted))
				    .collect(Collectors.toList());
			freeBySeance.put(3, onlyUnwanted);
		}//BUSY BY SEANCE ALLL
		
		
	}
	else {freeBySeance.put(3, matchingDates);}
	
}
}
if (ed.getSeance4() != null) {
if(seanceservice.IsRattrappage(ed.getSeance4()).getIdens()==idens){
	if (seanceservice.IsRattrappage(ed.getSeance4()).getIsRattrapage()==Aceptee.oui) {
		if(matchingDates.contains(ratservice.getDateAffRatt(ed.getSeance4()))) {
			LocalDate unwanted = ratservice.getDateAffRatt(ed.getSeance4());
			List<LocalDate> onlyUnwanted = matchingDates.stream()
				    .filter(d -> d.equals(unwanted))
				    .collect(Collectors.toList());
			freeBySeance.put(4, onlyUnwanted);
		}//BUSY BY SEANCE ALLL
		
		
	}
	else {freeBySeance.put(4, matchingDates);}
	
}
}
if (ed.getSeance5() != null) {
if(seanceservice.IsRattrappage(ed.getSeance5()).getIdens()==idens){
	if (seanceservice.IsRattrappage(ed.getSeance5()).getIsRattrapage()==Aceptee.oui) {
		if(matchingDates.contains(ratservice.getDateAffRatt(ed.getSeance5()))) {
			LocalDate unwanted = ratservice.getDateAffRatt(ed.getSeance5());
			List<LocalDate> onlyUnwanted = matchingDates.stream()
				    .filter(d -> d.equals(unwanted))
				    .collect(Collectors.toList());
			freeBySeance.put(5, onlyUnwanted);
		}//BUSY BY SEANCE ALLL
		
		
	}
	else {freeBySeance.put(5, matchingDates);}
	
}
}

if (result.containsKey(dow)) {
 // merge new freeBySeance into the existing map for this day
 result.get(dow).putAll(freeBySeance);
} else {
 // first time we see this day
 result.put(dow, freeBySeance);
}
}

return result; 
		 
		 		 
} 
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	
	    
	    
	    
	    
	    
	    
	    
	    
	    

}

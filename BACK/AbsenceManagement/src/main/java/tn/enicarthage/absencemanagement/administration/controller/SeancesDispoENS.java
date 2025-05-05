package tn.enicarthage.absencemanagement.administration.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import tn.enicarthage.absencemanagement.administration.model.EmploiTempsRowsDTO;
import tn.enicarthage.absencemanagement.administration.service.EmploiTempsService;
import tn.enicarthage.absencemanagement.administration.service.RattrapageService;
import tn.enicarthage.absencemanagement.administration.service.SeanceDispoENSERVICE;
import tn.enicarthage.absencemanagement.administration.service.SeanceService;
import tn.enicarthage.absencemanagement.enseignants.model.Aceptee;
import tn.enicarthage.absencemanagement.enseignants.model.SeanceInfos;
import tn.enicarthage.absencemanagement.etudiants.model.Classe;
import tn.enicarthage.absencemanagement.etudiants.model.Groupe;
import tn.enicarthage.absencemanagement.etudiants.model.Specialite;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class SeancesDispoENS {
	@Autowired
	private final EmploiTempsService servicee;
	
	@Autowired
	private SeanceDispoENSERVICE service;
	@Autowired
	private final SeanceService seanceservice;
	@Autowired
	private final RattrapageService ratservice;
	@GetMapping("/SeancesDispoENS")
	 public Map<DayOfWeek, Map<Integer, List<LocalDate>>>  fetchTimetable(
		       
		        @RequestParam 
		        @DateTimeFormat(pattern = "dd/MM/yyyy")
		        LocalDate start,
		        @RequestParam 
		        @DateTimeFormat(pattern = "dd/MM/yyyy")
		        LocalDate end,
		        @RequestParam Long idens
		        //il faut ajouter 
		    ){
		 return service.fetchTimetable(start, end, idens);
		 
	 }
	
	@GetMapping("/timetable/ens")
    public Map<DayOfWeek, Map<Integer, SeanceInfos>> fetchTimetableENS(@RequestParam Long idens) {
        List<EmploiTempsRowsDTO> emploi = servicee.getAllRows().stream()
                .sorted(Comparator.comparing(EmploiTempsRowsDTO::getJourr))
                .collect(Collectors.toList());

        // Initialize result map
        Map<DayOfWeek, Map<Integer, SeanceInfos>> result = new EnumMap<>(DayOfWeek.class);

        // Process each timetable row
        for (EmploiTempsRowsDTO ed : emploi) {
            DayOfWeek dow = ed.getJourr().toDayOfWeek();
            Map<Integer, SeanceInfos> seanceMap = result.computeIfAbsent(dow, k -> new HashMap<>());

            // Process each séance slot (1 to 5)
            processSeance(ed, 1, ed.getSeance1(), idens, seanceMap);
            processSeance(ed, 2, ed.getSeance2(), idens, seanceMap);
            processSeance(ed, 3, ed.getSeance3(), idens, seanceMap);
            processSeance(ed, 4, ed.getSeance4(), idens, seanceMap);
            processSeance(ed, 5, ed.getSeance5(), idens, seanceMap);
        }

        return result;
    }

    private void processSeance(EmploiTempsRowsDTO ed, int numSeance, Integer seanceId, Long idens,
            Map<Integer, SeanceInfos> seanceMap) {
        if (seanceId == null) {
            return; // Skip if no séance
        }

        // Check if the séance belongs to the teacher
        var rattrapage = seanceservice.IsRattrappage(seanceId);
        if (rattrapage.getIdens() != idens) {
            return; // Skip if not for this teacher
        }

        // Include séance if it's not a rattrapage or is a future rattrapage
        boolean isFutureRattrapage = rattrapage.getIsRattrapage() == Aceptee.oui
                && ratservice.getDateAffRatt(seanceId).isAfter(LocalDate.now());
        boolean isNotRattrapage = rattrapage.getIsRattrapage() != Aceptee.oui;

        if (isFutureRattrapage || isNotRattrapage) {
            SeanceInfos seanceInfo = new SeanceInfos(
                    numSeance,
                    ed.getClasse(),
                    ed.getSpecialite(),
                    ed.getGroupe(),
                    seanceId);
            seanceMap.put(numSeance, seanceInfo);
        }
    }
	

}

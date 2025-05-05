package tn.enicarthage.absencemanagement.administration.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import tn.enicarthage.absencemanagement.administration.model.AbsenceDTO;
import tn.enicarthage.absencemanagement.administration.model.EnseignantDTO;
import tn.enicarthage.absencemanagement.administration.service.AbsenceService;
import tn.enicarthage.absencemanagement.administration.service.EnseignantService;
import tn.enicarthage.absencemanagement.enseignants.model.Absence;
import tn.enicarthage.absencemanagement.enseignants.model.AbsenceDTOENS;
import tn.enicarthage.absencemanagement.enseignants.model.Aceptee;
import tn.enicarthage.absencemanagement.enseignants.model.NewAbsenceRequest;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class AbsenceController {
	@Autowired
 private final AbsenceService absenceService;
	 @GetMapping("/admin/pending_absences")
	    public List<AbsenceDTO> getAllAbsences() {
	        return absenceService.getAllAbsences();
	 }
	 
	 
	 @GetMapping("/admin/processed_absences")
		public List<AbsenceDTO> getAllProcessedAbsences() {
			return absenceService.getAllProcessedAbsences();
		}
	 
	 
	 @GetMapping("/etudiant/accepted_absences")
		public List<AbsenceDTO> getAllAcceptedAbs() {
			return absenceService.getAllAcceptedAbs();
		}
	 
	 
	 @PostMapping("/enseignant/creerAbs")
	    public ResponseEntity<Long> createAbsence(
	        @RequestBody NewAbsenceRequest req
	    ) {
	        Long id = absenceService.createAbsence(req);
	        return ResponseEntity
	            .created(URI.create("/api/absence/" + id))
	            .body(id);
	    }
	 
	 
	 
	 @GetMapping("/absences")
	    public List<AbsenceDTOENS> listByEnseignant(
	        @RequestParam Long enseignantId
	    ) {
	        return absenceService.getByEnseignant(enseignantId);
	    }
	 
	 
	 @PostMapping("/admin/absences/respond")
		public ResponseEntity<Void> respondToAbsence(
			@RequestParam Long absenceId,
			@RequestParam Aceptee acceptee
		) {
			absenceService.updateAbsenceAcceptee(absenceId, acceptee);
			return ResponseEntity.noContent().build();
		}
}

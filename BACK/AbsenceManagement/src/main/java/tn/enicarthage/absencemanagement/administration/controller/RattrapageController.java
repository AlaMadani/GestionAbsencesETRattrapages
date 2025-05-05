package tn.enicarthage.absencemanagement.administration.controller;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import tn.enicarthage.absencemanagement.administration.model.AbsenceDTO;
import tn.enicarthage.absencemanagement.administration.model.AccepterRattrapageRequest;
import tn.enicarthage.absencemanagement.administration.model.ProcessedRattrapageDTO;
import tn.enicarthage.absencemanagement.administration.model.RattrapageDTO;
import tn.enicarthage.absencemanagement.administration.service.AbsenceService;
import tn.enicarthage.absencemanagement.administration.service.RattrapageService;
import tn.enicarthage.absencemanagement.enseignants.model.NewRattrapageRequest;
import tn.enicarthage.absencemanagement.enseignants.model.RattrapageDTOENS;
import tn.enicarthage.absencemanagement.enseignants.model.numSeance;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class RattrapageController {
	@Autowired
	 private final RattrapageService rattrapageService;
		 @GetMapping("/admin/pending_rattrapages")
		    public List<RattrapageDTO> getAllAbsences() {
			 
			 
			 
		        return rattrapageService.getAllRattrapages();
		 }
		 @GetMapping("/etudiant/DateAffRatt")
		 public LocalDate getDateAff( @RequestParam int id) {
			 return rattrapageService.getDateAffRatt(id);
		 }
		 
		 @GetMapping("/admin/processed_rattrapages")
			public List<ProcessedRattrapageDTO> getAllProcessedRattrapages() {
				return rattrapageService.getAllProcessedRattrapages();
			}
		 
		 
		 @GetMapping("/etudiant/accepted_rattrapages")
			public List<ProcessedRattrapageDTO> getAllAcceptedRatt() {
				return rattrapageService.getAllAcceptedRatt();
			}
		 
		 
		 @PostMapping("/admin/accepter")
		    public ResponseEntity<Void> accepterRattrapage(
		    		@RequestBody AccepterRattrapageRequest req
		    ) {
			 rattrapageService.accepterEtCreerSeance(
					 req.rattrapageId(),
				        req.dateAff(),
				        req.seanceAff(),
				        req.salleId(),
				        req.enseignantId()
		        );
		        return ResponseEntity.noContent().build();
		    }
		 
		 
		  @PostMapping("/admin/rejeter")
		    public ResponseEntity<Void> rejeterRattrapage(
		        @RequestParam Long rattrapageId
		    ) {
			  rattrapageService.rejeterRattrapage(rattrapageId);
		        return ResponseEntity.noContent().build();
		    }
		  
		  
		  @PostMapping("/enseignant/creer")
		    public ResponseEntity<Long> createRattrapage(
		        @RequestBody NewRattrapageRequest req
		    ) {
		        Long newId =  rattrapageService.createRattrapage(req);
		        return ResponseEntity
		            .created(URI.create("/api/rattrapage/" + newId))
		            .body(newId);
		    }
		  
		  @GetMapping("/enseignant/rattrapages")
		    public List<RattrapageDTOENS> listByEnseignant(
		        @RequestParam Long enseignantId
		    ) {
		        return rattrapageService.getByEnseignant(enseignantId);
		    }

}

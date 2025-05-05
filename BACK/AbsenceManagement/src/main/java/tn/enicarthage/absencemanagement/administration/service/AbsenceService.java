package tn.enicarthage.absencemanagement.administration.service;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import tn.enicarthage.absencemanagement.administration.model.AbsenceDTO;
import tn.enicarthage.absencemanagement.administration.repository.AbsenceRepository;
import tn.enicarthage.absencemanagement.administration.repository.EnseignantRepository;
import tn.enicarthage.absencemanagement.enseignants.model.Absence;
import tn.enicarthage.absencemanagement.enseignants.model.AbsenceDTOENS;
import tn.enicarthage.absencemanagement.enseignants.model.Aceptee;
import tn.enicarthage.absencemanagement.enseignants.model.Enseignant;
import tn.enicarthage.absencemanagement.enseignants.model.NewAbsenceRequest;
@AllArgsConstructor
@Service
public class AbsenceService {
	@Autowired
	private final AbsenceRepository absenceRepository;
	@Autowired
	private final EnseignantRepository enseignantRepo;
	 public List<AbsenceDTO> getAllAbsences() {
	        return absenceRepository.findAllWithEnseignant();
	    }
	 
	 
	 public List<AbsenceDTO> getAllProcessedAbsences() {
			return absenceRepository.findAllProcessedWithEnseignant();
		}
	 
	 
	 public List<AbsenceDTO> getAllAcceptedAbs() {
			return absenceRepository.findAllAcceptedAbs();
		}
	 
	 
	 
	 @Transactional
	    public Long createAbsence(NewAbsenceRequest req) {
	        Absence abs = new Absence();

	        // dates début/fin
	        abs.setDate_db(Date.valueOf(req.getDateDebut()));
	        abs.setDate_fin(Date.valueOf(req.getDateFin()));

	        // créneaux début/fin
	        abs.setSeancedb(req.getSeanceDebut());
	        abs.setSeancefin(req.getSeanceFin());

	        // acceptee et pinned = null ou default non?
	        abs.setAcceptee(null);
	        abs.setPinned(null);

	        // lien enseignant obligatoire
	        var ens = enseignantRepo.findById(req.getEnseignantId())
	            .orElseThrow(() -> new IllegalArgumentException(
	                "Enseignant introuvable : " + req.getEnseignantId()
	            ));
	        abs.setEnseignant(ens);


	        // pas de liste d’étudiants épingleurs pour l’instant
	        // absencesEpinglees géré ailleurs

	        Absence saved = absenceRepository.save(abs);
	        return saved.getId();
	    }
	 
	 
	 
	 public List<AbsenceDTOENS> getByEnseignant(Long enseignantId) {
	        return absenceRepository.findByEnseignant_Id(enseignantId).stream()
	            .map(this::toDto)
	            .collect(Collectors.toList());
	    }
	    private AbsenceDTOENS toDto(Absence a) {
	        return new AbsenceDTOENS(
	            a.getId(),
	            a.getDate_db().toLocalDate(),
	            a.getDate_fin().toLocalDate(),
	            a.getSeancedb(),
	            a.getSeancefin(),
	            a.getAcceptee() != null ? a.getAcceptee().name() : null,
	            a.getPinned()   != null ? a.getPinned().name()   : null
	        );
	    }
	    
	    
	    @Transactional
	    public void updateAbsenceAcceptee(Long absenceId, Aceptee acceptee) {
	        Absence absence = absenceRepository.findById(absenceId)
	                .orElseThrow(() -> new IllegalArgumentException("Absence introuvable : " + absenceId));

	        // Update acceptee status
	        absence.setAcceptee(acceptee);

	        // Increment nbAbsences if accepted
	        if (acceptee == Aceptee.oui) {
	            Enseignant enseignant = absence.getEnseignant();
	            if (enseignant != null) {
	                enseignant.setNbAbsences(enseignant.getNbAbsences() + 1);
	                enseignantRepo.save(enseignant);
	            } else {
	                throw new IllegalStateException("Enseignant non associé à l'absence : " + absenceId);
	            }
	        }

	        // Save the updated absence
	        absenceRepository.save(absence);
	    }
	
}

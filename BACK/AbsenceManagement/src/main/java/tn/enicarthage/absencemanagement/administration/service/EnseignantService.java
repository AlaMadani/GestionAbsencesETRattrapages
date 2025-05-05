package tn.enicarthage.absencemanagement.administration.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import tn.enicarthage.absencemanagement.administration.model.EnseignantDTO;
import tn.enicarthage.absencemanagement.administration.repository.EnseignantRepository;
import tn.enicarthage.absencemanagement.enseignants.model.Enseignant;

@AllArgsConstructor
@Service
public class EnseignantService {
	@Autowired
 private final EnseignantRepository enseignantRepository;
	 
	 public List<Enseignant> getAllEnseignants() {
	        return enseignantRepository.findAll();
	    }

	 public EnseignantDTO findByEmail(String email) {
	        Enseignant enseignant = enseignantRepository.findByEmail(email)
	                .orElse(null);
	        if (enseignant == null) {
	            return null;
	        }
	        return toDTO(enseignant);
	    }

	    private EnseignantDTO toDTO(Enseignant enseignant) {
	        return new EnseignantDTO(
	                enseignant.getId(),
	                enseignant.getNom(),
	                enseignant.getPrenom(),
	                enseignant.getEmail(),
	                enseignant.getMotdepass(),
	                enseignant.getGrade(),
	                enseignant.getNum_tel(),
	                enseignant.getNbAbsences()
	        );
	    }
}

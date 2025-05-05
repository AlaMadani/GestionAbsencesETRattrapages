package tn.enicarthage.absencemanagement.administration.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import tn.enicarthage.absencemanagement.administration.model.AbsenceDTO;
import tn.enicarthage.absencemanagement.administration.model.SeanceDTO;
import tn.enicarthage.absencemanagement.administration.model.SeanceInfo;
import tn.enicarthage.absencemanagement.administration.repository.AbsenceRepository;
import tn.enicarthage.absencemanagement.administration.repository.SeanceRepository;
import tn.enicarthage.absencemanagement.enseignants.model.Aceptee;
import tn.enicarthage.absencemanagement.etudiants.model.Seance;

@AllArgsConstructor
@Service
public class SeanceService {
	@Autowired
	public final SeanceRepository seancerepository;
	public  SeanceInfo IsRattrappage(int ids) {
        return seancerepository.IsRattrapage(ids);
    }
	
	
	public SeanceDTO getSeanceById(int id) {
        Seance seance = seancerepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Seance not found with id: " + id));
        return toSeanceDTO(seance);
    }

    private SeanceDTO toSeanceDTO(Seance seance) {
        return new SeanceDTO(
                seance.getId(),
                seance.getMatiere(),
                seance.getISrattrappage(),
                seance.getEnseignant().getNom(),
                seance.getEnseignant().getPrenom(),
                seance.getSalle().getNoSalle(),
                seance.getRattrapage() != null ? seance.getRattrapage().getId() : null
        );
    }

}

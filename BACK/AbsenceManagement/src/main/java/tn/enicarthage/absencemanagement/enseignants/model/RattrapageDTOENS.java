package tn.enicarthage.absencemanagement.enseignants.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.enicarthage.absencemanagement.administration.model.Salle;
import tn.enicarthage.absencemanagement.etudiants.model.Classe;
import tn.enicarthage.absencemanagement.etudiants.model.Groupe;
import tn.enicarthage.absencemanagement.etudiants.model.Specialite;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RattrapageDTOENS {
	 private Long id;
	    private LocalDate dateDebut;
	    private LocalDate dateFin;
	    private numSeance seanceDebut;
	    private numSeance seanceFin;
	    private String acceptee;      // “oui” / “non” / null
	    private LocalDate dateAffectee;
	    private numSeance seanceAffectee;
	    private String pinned;        // idem
	    private Classe classe;
	    private Specialite specialite;
	    private Groupe groupe;
	    private String salleaff;
}

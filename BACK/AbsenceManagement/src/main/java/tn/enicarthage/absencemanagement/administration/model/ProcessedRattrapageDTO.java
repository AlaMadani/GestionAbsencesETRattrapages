package tn.enicarthage.absencemanagement.administration.model;

import java.sql.Date;
import java.time.LocalDate;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import tn.enicarthage.absencemanagement.enseignants.model.Aceptee;
import tn.enicarthage.absencemanagement.enseignants.model.numSeance;
import tn.enicarthage.absencemanagement.etudiants.model.Classe;
import tn.enicarthage.absencemanagement.etudiants.model.Groupe;
import tn.enicarthage.absencemanagement.etudiants.model.Specialite;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProcessedRattrapageDTO extends RattrapageDTO {
	
	private LocalDate date_aff;
	private numSeance seanceAff;
	private String salleId;

	public ProcessedRattrapageDTO(
			Long id,
			Date date_db,
			Date date_fin,
			numSeance seancedb,
			numSeance seancefin,
			Aceptee acceptee,
			Classe classe,
			Specialite specialite,
			Groupe groupe,
			Long idens,
			String nom,
			String prenom,
			String grade,
			String num_tel,
			int nbAbsences,
			LocalDate date_aff,
			numSeance seanceAff,
			String salleId
	) {
		super(id, date_db, date_fin, seancedb, seancefin, acceptee, classe, specialite, groupe, idens, nom, prenom, grade, num_tel, nbAbsences);
		this.date_aff = date_aff;
		this.seanceAff = seanceAff;
		this.salleId = salleId;
	}
}
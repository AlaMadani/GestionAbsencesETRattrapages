package tn.enicarthage.absencemanagement.administration.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import tn.enicarthage.absencemanagement.enseignants.model.Aceptee;
import tn.enicarthage.absencemanagement.enseignants.model.numSeance;
import tn.enicarthage.absencemanagement.etudiants.model.Classe;
import tn.enicarthage.absencemanagement.etudiants.model.Groupe;
import tn.enicarthage.absencemanagement.etudiants.model.Seance;
import tn.enicarthage.absencemanagement.etudiants.model.Specialite;
import tn.enicarthage.absencemanagement.etudiants.model.jour;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EmploiTempsRowsDTO {
	 private jour jourr;
	 private Classe classe;
	 private Specialite specialite;
	 private Groupe groupe;
	 private Integer seance1;
	 private Integer seance2;
	 private Integer seance3;
	 private Integer seance4;
	 private Integer seance5;
}

package tn.enicarthage.absencemanagement.administration.model;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import tn.enicarthage.absencemanagement.enseignants.model.Aceptee;
import tn.enicarthage.absencemanagement.enseignants.model.Enseignant;
import tn.enicarthage.absencemanagement.enseignants.model.numSeance;
import tn.enicarthage.absencemanagement.etudiants.model.Classe;
import tn.enicarthage.absencemanagement.etudiants.model.Etudiant;
import tn.enicarthage.absencemanagement.etudiants.model.Groupe;
import tn.enicarthage.absencemanagement.etudiants.model.Seance;
import tn.enicarthage.absencemanagement.etudiants.model.Specialite;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RattrapageDTO {
	private Long id;
	private Date date_db;
	private Date date_fin;
	private numSeance seancedb;
	private numSeance seancefin;
	private Aceptee acceptee;
	private Classe classe;
	private Specialite specialite;
	private Groupe groupe;
	private Long idens;
	private String nom;
	private String prenom;
	private String grade;
	private String num_tel;
	private int nbAbsences;

}

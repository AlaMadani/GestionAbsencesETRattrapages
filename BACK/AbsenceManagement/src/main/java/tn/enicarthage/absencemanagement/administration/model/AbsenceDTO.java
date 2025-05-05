package tn.enicarthage.absencemanagement.administration.model;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
import tn.enicarthage.absencemanagement.etudiants.model.Etudiant;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AbsenceDTO {
	private Long id;
	private Date date_db;
	private Date date_fin;
	private numSeance seancedb;
	private numSeance seancefin;
	private Aceptee acceptee;
	private Long idens;
	private String nom;
	private String prenom;
	private String grade;
	private String num_tel;
	private int nbAbsences;
	
	
	


}

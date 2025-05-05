package tn.enicarthage.absencemanagement.enseignants.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import tn.enicarthage.absencemanagement.etudiants.model.Seance;
import tn.enicarthage.absencemanagement.etudiants.model.User;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Enseignant extends User implements Serializable{
	private static final long serialVersionUID = 1L;
	private String grade;
	private String num_tel;
	private int nbAbsences;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy="enseignant")
	private Set<Seance> seances;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy="enseignant")
	private Set<Absence> absences;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy="enseignant")
	private Set<Rattrappage> rattrappages;
	

}

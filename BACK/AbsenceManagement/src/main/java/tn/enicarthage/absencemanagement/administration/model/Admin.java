package tn.enicarthage.absencemanagement.administration.model;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import tn.enicarthage.absencemanagement.etudiants.model.Classe;
import tn.enicarthage.absencemanagement.etudiants.model.Groupe;

import tn.enicarthage.absencemanagement.etudiants.model.Specialite;
import tn.enicarthage.absencemanagement.etudiants.model.User;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Admin extends User implements Serializable{
	private static final long serialVersionUID = 1L;
	private String numtel;
	
	
	

}

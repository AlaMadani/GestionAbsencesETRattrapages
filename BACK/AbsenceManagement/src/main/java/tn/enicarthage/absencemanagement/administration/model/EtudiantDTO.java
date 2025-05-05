package tn.enicarthage.absencemanagement.administration.model;

import java.util.List;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class EtudiantDTO{
	private Long id;
	private String nom;
	private String prenom;
	private String email;
	private String motdepass;
	@Enumerated(EnumType.STRING)
	private Classe classe;
	@Enumerated(EnumType.STRING)
	private Specialite specialite;
	@Enumerated(EnumType.STRING)
	private Groupe groupe;
	
}

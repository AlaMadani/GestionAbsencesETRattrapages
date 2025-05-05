package tn.enicarthage.absencemanagement.administration.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import tn.enicarthage.absencemanagement.enseignants.model.Aceptee;
import tn.enicarthage.absencemanagement.enseignants.model.Enseignant;
import tn.enicarthage.absencemanagement.enseignants.model.Rattrappage;
import tn.enicarthage.absencemanagement.enseignants.model.numSeance;
import tn.enicarthage.absencemanagement.etudiants.model.Etudiant;
import tn.enicarthage.absencemanagement.etudiants.model.Seance;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Salle implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name="Salle")
	private String noSalle;
	
	
	@OneToMany( cascade =
			CascadeType.ALL, mappedBy = "salle")
			private List<Rattrappage> listeRattrapages ;
	
	
	@OneToMany( cascade =
			CascadeType.ALL, mappedBy = "salle")
			private List<Seance> listeSeances ;
	
	
	@OneToMany( cascade =
			CascadeType.ALL, mappedBy = "salle")
			private List<Seance> listeEvenements ;

}

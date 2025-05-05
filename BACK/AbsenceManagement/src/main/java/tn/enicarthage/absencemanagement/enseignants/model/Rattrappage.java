package tn.enicarthage.absencemanagement.enseignants.model;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import tn.enicarthage.absencemanagement.administration.model.Salle;
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
@Entity

public class Rattrappage implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name="abs_ratt_id")
	private Long id;
	@Temporal(TemporalType.DATE)
	@Column(name = "date_au_plus_tot")
	private Date date_db;
	@Temporal(TemporalType.DATE)
	@Column(name = "date_au_plus_tard")
	private Date date_fin;
	@Enumerated(EnumType.STRING)
	private numSeance seancedb;
	@Enumerated(EnumType.STRING)
	private numSeance seancefin;
	@Enumerated(EnumType.STRING)
	@Column(nullable=true)
	private Aceptee acceptee;
	@Enumerated(EnumType.STRING)
	private Classe classe;
	@Enumerated(EnumType.STRING)
	private Specialite specialite;
	@Enumerated(EnumType.STRING)
	private Groupe groupe;
	@Temporal(TemporalType.DATE)
	@Column(name = "date_affectee",nullable=true)
	private LocalDate date_aff;
	@Column(nullable=true)
	@Enumerated(EnumType.STRING)
	private numSeance seanceAff;
	@Enumerated(EnumType.STRING)
	@Column(nullable=true)
	private Aceptee pinned;
	private String Matiere;
	
	
	@ManyToOne(cascade = CascadeType.ALL
			,/*fetch = FetchType.EAGER*/fetch = FetchType.LAZY)
			private Enseignant enseignant;
	
	
	@OneToOne(mappedBy = "rattrapage")
			private Seance SeanceRatt;
	
	 @ManyToMany(mappedBy = "rattrapagesEpinglees")
	    private List<Etudiant> etudiantsQuiEpinglent;
	 
	
	 @ManyToOne(cascade = CascadeType.ALL
			 ,fetch = FetchType.EAGER/* fetch =
			 FetchType.LAZY*/)
			 private Salle salle;
	
	

}

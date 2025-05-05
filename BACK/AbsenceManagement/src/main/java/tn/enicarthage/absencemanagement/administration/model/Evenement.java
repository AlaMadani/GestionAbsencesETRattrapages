package tn.enicarthage.absencemanagement.administration.model;

import java.io.Serializable;
import java.sql.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import tn.enicarthage.absencemanagement.enseignants.model.Aceptee;
import tn.enicarthage.absencemanagement.enseignants.model.numSeance;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Evenement implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "Event_id")
	private int id;
	private String Titre;
	private String description;
	@Temporal(TemporalType.DATE)
	@Column(name = "date_de_debut")
	private Date date_db;
	@Temporal(TemporalType.DATE)
	@Column(name = "date_de_fin")
	private Date date_fin;
	@Enumerated(EnumType.STRING)
	private numSeance seancedb;
	@Enumerated(EnumType.STRING)
	private numSeance seancefin;
	@Enumerated(EnumType.STRING)
	@Column(nullable=true)
	private Aceptee ISannulee;
	
	
	
	 @ManyToOne(cascade = CascadeType.ALL
			 ,fetch = FetchType.EAGER/* fetch =
			 FetchType.LAZY*/)
			 private Salle salle;
	

}

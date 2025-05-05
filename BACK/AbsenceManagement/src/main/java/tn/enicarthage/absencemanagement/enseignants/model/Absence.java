package tn.enicarthage.absencemanagement.enseignants.model;

import java.io.Serializable;
import java.sql.Date;
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
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
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
import tn.enicarthage.absencemanagement.etudiants.model.Etudiant;



@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity


public  class Absence implements Serializable{
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
	@Column(nullable=true)
	private Aceptee pinned;
	
	
	@ManyToOne(cascade = CascadeType.ALL
			,/*fetch = FetchType.EAGER*/fetch = FetchType.LAZY)
			private Enseignant enseignant;
	
	
	@JsonIgnore
	 @ManyToMany(mappedBy = "absencesEpinglees")
	    private List<Etudiant> etudiantsQuiEpinglent;
	
	


}

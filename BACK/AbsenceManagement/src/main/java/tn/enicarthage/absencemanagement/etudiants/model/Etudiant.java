package tn.enicarthage.absencemanagement.etudiants.model;
import jakarta.persistence.JoinColumn;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import tn.enicarthage.absencemanagement.enseignants.model.Absence;
import tn.enicarthage.absencemanagement.enseignants.model.Rattrappage;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Etudiant extends User implements Serializable {
	private static final long serialVersionUID = 1L;
	@Enumerated(EnumType.STRING)
	private Classe classe;
	@Enumerated(EnumType.STRING)
	private Specialite specialite;
	@Enumerated(EnumType.STRING)
	private Groupe groupe;
	
	
	 @ManyToMany
	    @JoinTable(
	      name = "abs_epinglee",
	      joinColumns = @JoinColumn(name = "id_usr"),
	      inverseJoinColumns = @JoinColumn(name = "id_abs")
	    )
	    private List<Absence> absencesEpinglees;
	 
	 @ManyToMany
	    @JoinTable(
	      name = "ratt_epinglee",
	      joinColumns = @JoinColumn(name = "id_usr"),
	      inverseJoinColumns = @JoinColumn(name = "id_ratt")
	    )
	    private List<Rattrappage> rattrapagesEpinglees;
	
	
	
}

package tn.enicarthage.absencemanagement.etudiants.model;



import com.fasterxml.jackson.annotation.JsonIgnore;

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
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import tn.enicarthage.absencemanagement.administration.model.Salle;
import tn.enicarthage.absencemanagement.enseignants.model.Aceptee;
import tn.enicarthage.absencemanagement.enseignants.model.Enseignant;
import tn.enicarthage.absencemanagement.enseignants.model.Rattrappage;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Seance {
@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
private int id;
private String Matiere;


@Enumerated(EnumType.STRING)
@Column(nullable=true)
private Aceptee ISrattrappage;

@ManyToOne(cascade = CascadeType.ALL
,fetch = FetchType.EAGER)
private Enseignant enseignant;


@OneToOne
private Rattrappage rattrapage;



@ManyToOne(cascade = CascadeType.ALL
,fetch = FetchType.EAGER/* fetch =
FetchType.LAZY*/)
private Salle salle;
}

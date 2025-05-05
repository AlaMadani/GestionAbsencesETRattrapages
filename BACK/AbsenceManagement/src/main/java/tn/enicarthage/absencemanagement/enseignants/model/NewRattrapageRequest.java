package tn.enicarthage.absencemanagement.enseignants.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.enicarthage.absencemanagement.etudiants.model.Classe;
import tn.enicarthage.absencemanagement.etudiants.model.Groupe;
import tn.enicarthage.absencemanagement.etudiants.model.Specialite;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NewRattrapageRequest {
  
  private Classe classe;
  private Specialite specialite;
  private Groupe groupe;

 // @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
  private LocalDate dateDebut;

  //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
  private LocalDate dateFin;

  private Long enseignantId;
  private String matiere;
}

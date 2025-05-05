package tn.enicarthage.absencemanagement.enseignants.model;

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
public class SeanceInfos {
    private Integer numseance;
    private Classe classe;
    private Specialite specialite;
    private Groupe groupe;
    private Integer seanceId;
}
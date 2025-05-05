package tn.enicarthage.absencemanagement.administration.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.enicarthage.absencemanagement.enseignants.model.Aceptee;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SeanceDTO {
    private int id;
    private String matiere;
    private Aceptee isRattrapage;
    private String enseignantNom;
    private String enseignantPrenom;
    private String salleNumero;
    private Long rattrapageId;
}
package tn.enicarthage.absencemanagement.administration.model;

import java.sql.Date;

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
public class SeanceInfo {
	private Aceptee IsRattrapage;
	private Long idens;
	private String salle;

}

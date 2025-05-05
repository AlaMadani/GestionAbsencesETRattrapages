package tn.enicarthage.absencemanagement.administration.model;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import tn.enicarthage.absencemanagement.enseignants.model.numSeance;
import tn.enicarthage.absencemanagement.etudiants.model.jour;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SeancesDispoDTO {
	private Map<DayOfWeek, Map<Integer, List<LocalDate>>> seancesDispo;
	


}

package tn.enicarthage.absencemanagement.etudiants.model;

import java.time.DayOfWeek;

public enum jour  implements Comparable<jour>  {
  LUNDI, MARDI, MERCREDI, JEUDI, VENDREDI, SAMEDI;
  
  
  public DayOfWeek toDayOfWeek() {
	    return switch(this) {
	      case LUNDI    -> DayOfWeek.MONDAY;
	      case MARDI    -> DayOfWeek.TUESDAY;
	      case MERCREDI -> DayOfWeek.WEDNESDAY;
	      case JEUDI    -> DayOfWeek.THURSDAY;
	      case VENDREDI -> DayOfWeek.FRIDAY;
	      case SAMEDI   -> DayOfWeek.SATURDAY;
	    };
	  }
}

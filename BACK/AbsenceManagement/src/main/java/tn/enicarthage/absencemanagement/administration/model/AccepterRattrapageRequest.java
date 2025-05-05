package tn.enicarthage.absencemanagement.administration.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import tn.enicarthage.absencemanagement.enseignants.model.numSeance;

public record AccepterRattrapageRequest(
	    Long rattrapageId,
	    
	    LocalDate dateAff,
	    numSeance seanceAff,
	    String salleId,
	    Long enseignantId
	) {}


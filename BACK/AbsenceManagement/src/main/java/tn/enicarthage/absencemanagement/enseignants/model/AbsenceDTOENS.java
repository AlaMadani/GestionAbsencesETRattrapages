package tn.enicarthage.absencemanagement.enseignants.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AbsenceDTOENS {
	 private Long id;
	    private LocalDate dateDebut;
	    private LocalDate dateFin;
	    private numSeance seanceDebut;
	    private numSeance seanceFin;
	    private String acceptee;     
	    private String pinned;  

}

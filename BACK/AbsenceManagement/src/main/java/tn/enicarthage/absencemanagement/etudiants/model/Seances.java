package tn.enicarthage.absencemanagement.etudiants.model;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Embeddable
public class Seances implements Serializable{
	private static final long serialVersionUID = 1L;


	 @ManyToOne
	    @JoinColumn(name = "seance1_id", nullable = true)
	    private Seance seance1;

	    @ManyToOne
	    @JoinColumn(name = "seance2_id", nullable = true)
	    private Seance seance2;

	    @ManyToOne
	    @JoinColumn(name = "seance3_id", nullable = true)
	    private Seance seance3;

	    @ManyToOne
	    @JoinColumn(name = "seance4_id", nullable = true)
	    private Seance seance4;

	    @ManyToOne
	    @JoinColumn(name = "seance5_id", nullable = true)
	    private Seance seance5;
}

package tn.enicarthage.absencemanagement.administration.model;

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
public class EnseignantDTO {
	private Long id;
	private String nom;
	private String prenom;
	private String email;
	private String motdepass;
	private String grade;
	private String num_tel;
	private int nbAbsences;
}

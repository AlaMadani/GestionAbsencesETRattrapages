package tn.enicarthage.absencemanagement.etudiants.model;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class EmploiTempsId implements Serializable {
	private static final long serialVersionUID = 1L;
	@Enumerated(EnumType.STRING)
    private jour jourr;

    @Enumerated(EnumType.STRING)
    private Classe classe;

    @Enumerated(EnumType.STRING)
    private Specialite specialite;

    @Enumerated(EnumType.STRING)
    private Groupe groupe;

}

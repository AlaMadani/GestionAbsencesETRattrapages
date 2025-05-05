// tn/enicarthage/absencemanagement/model/SignupRequest.java
package tn.enicarthage.absencemanagement.administration.model;

import lombok.Data;
import tn.enicarthage.absencemanagement.etudiants.model.Classe;
import tn.enicarthage.absencemanagement.etudiants.model.Groupe;
import tn.enicarthage.absencemanagement.etudiants.model.Specialite;

@Data
public class SignupRequest {
    private String email;
    private String password;
    private String nom;
    private String prenom;
    private UserType userType;

    // Optional fields for specific user types
    private String grade; // For Enseignant
    private String numTel; // For Enseignant

    public enum UserType {
        ETUDIANT, ENSEIGNANT, ADMIN
    }
}
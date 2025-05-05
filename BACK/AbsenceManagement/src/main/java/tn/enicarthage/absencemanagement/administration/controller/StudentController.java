package tn.enicarthage.absencemanagement.administration.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import tn.enicarthage.absencemanagement.administration.model.EtudiantDTO;
import tn.enicarthage.absencemanagement.administration.service.StudentService;
import tn.enicarthage.absencemanagement.etudiants.model.Classe;
import tn.enicarthage.absencemanagement.etudiants.model.Etudiant;
import tn.enicarthage.absencemanagement.etudiants.model.Groupe;
import tn.enicarthage.absencemanagement.etudiants.model.Specialite;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class StudentController {
    @Autowired
    private final StudentService studentService;
    
    @GetMapping("/admin/students")
    public List<EtudiantDTO> getAllStudents() {
        return studentService.getAllStudents().stream()
                .map(e -> new EtudiantDTO(e.getId(), e.getNom(), e.getPrenom(), e.getEmail(), e.getMotdepass(), e.getClasse(), e.getSpecialite(), e.getGroupe()))
                .toList();
    }

    @GetMapping("/etudiantt/{id}/details")
    public ResponseEntity<?> getStudentDetailsById(@PathVariable Long id) {
        Etudiant etudiant = studentService.getStudentById(id);
        if (etudiant == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of(
            "classe", etudiant.getClasse(),
            "specialite", etudiant.getSpecialite(),
            "groupe", etudiant.getGroupe()
        ));
    }
    
    
    
    
    @PutMapping("/etudiant/{id}/details")
    public ResponseEntity<?> updateStudentDetails(@PathVariable Long id, @RequestBody Map<String, String> updates) {
        // Validate and convert classe
        Classe classe;
        try {
            classe = updates.get("classe") != null ? Classe.valueOf(updates.get("classe").toUpperCase()) : null;
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid classe value: " + updates.get("classe")));
        }

        // Validate and convert specialite
        Specialite specialite;
        try {
            specialite = updates.get("specialite") != null ? Specialite.valueOf(updates.get("specialite").toUpperCase()) : null;
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid specialite value: " + updates.get("specialite")));
        }

        // Validate and convert groupe
        Groupe groupe;
        try {
            groupe = updates.get("groupe") != null ? Groupe.valueOf(updates.get("groupe").toUpperCase()) : null;
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid groupe value: " + updates.get("groupe")));
        }

        Etudiant updatedEtudiant = studentService.updateStudentDetails(id, classe, specialite, groupe);
        if (updatedEtudiant == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of(
            "classe", updatedEtudiant.getClasse().toString(),
            "specialite", updatedEtudiant.getSpecialite().toString(),
            "groupe", updatedEtudiant.getGroupe().toString()
        ));
    }
}
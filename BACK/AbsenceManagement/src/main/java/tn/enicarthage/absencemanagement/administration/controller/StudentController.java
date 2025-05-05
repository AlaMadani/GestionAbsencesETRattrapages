package tn.enicarthage.absencemanagement.administration.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import tn.enicarthage.absencemanagement.administration.model.EtudiantDTO;
import tn.enicarthage.absencemanagement.administration.service.StudentService;
import tn.enicarthage.absencemanagement.etudiants.model.Etudiant;
@AllArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class StudentController {
	@Autowired
	 private final StudentService studentService;
	 
	 @GetMapping("/students")
	    public List<EtudiantDTO> getAllStudents() {
	        return studentService.getAllStudents().stream()
	                .map(e -> new EtudiantDTO(e.getId(), e.getNom(), e.getPrenom(),e.getEmail(),e.getMotdepass(),e.getClasse(),e.getSpecialite(),e.getGroupe()))
	                .toList();
	    }

}

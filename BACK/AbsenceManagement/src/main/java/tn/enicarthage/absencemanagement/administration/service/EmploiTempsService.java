package tn.enicarthage.absencemanagement.administration.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import tn.enicarthage.absencemanagement.administration.model.EmploiTempsRowsDTO;
import tn.enicarthage.absencemanagement.administration.model.ResourceNotFoundException;
import tn.enicarthage.absencemanagement.administration.repository.EmploiTempsRepository;
import tn.enicarthage.absencemanagement.etudiants.model.Classe;
import tn.enicarthage.absencemanagement.etudiants.model.EmploiTemps;
import tn.enicarthage.absencemanagement.etudiants.model.Groupe;
import tn.enicarthage.absencemanagement.etudiants.model.Specialite;

@Service
@AllArgsConstructor
public class EmploiTempsService {
	@Autowired
	 private final EmploiTempsRepository repository;

	 public List<EmploiTempsRowsDTO> getTimetableRows(
		        Classe classe,
		        Specialite specialite,
		        Groupe groupe
		    ) {
		        return repository.findRowsByClasseSpecialiteGroupe(classe, specialite, groupe);
		    }
	 public List<EmploiTempsRowsDTO> getAllRows(
		        
		    ) {
		        return repository.findAllRows();
		    }
	 
	 
	 public List<String> getAllClasses() {
	        return repository.findAllClasses().stream()
	                .map(Enum::name)
	                .collect(Collectors.toList());
	    }

	    public List<String> getSpecialitesByClasse(Classe classe) {
	        return repository.findSpecialitesByClasse(classe).stream()
	                .map(Enum::name)
	                .collect(Collectors.toList());
	    }

	    public List<String> getGroupesByClasseAndSpecialite(Classe classe, Specialite specialite) {
	        return repository.findGroupesByClasseAndSpecialite(classe, specialite).stream()
	                .map(Enum::name)
	                .collect(Collectors.toList());
	    }
}

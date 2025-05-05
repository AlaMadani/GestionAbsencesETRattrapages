package tn.enicarthage.absencemanagement.administration.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import tn.enicarthage.absencemanagement.administration.model.Salle;
import tn.enicarthage.absencemanagement.administration.repository.SallesRepository;

@AllArgsConstructor
@Service
public class SalleService {
	@Autowired
	private SallesRepository repo;
	public List<String> getALL(){
		return repo.findAllSalles();
	}

}

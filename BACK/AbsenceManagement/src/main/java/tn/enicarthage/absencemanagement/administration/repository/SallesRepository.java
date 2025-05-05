package tn.enicarthage.absencemanagement.administration.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tn.enicarthage.absencemanagement.administration.model.EmploiTempsRowsDTO;
import tn.enicarthage.absencemanagement.administration.model.Salle;
import tn.enicarthage.absencemanagement.etudiants.model.EmploiTemps;
import tn.enicarthage.absencemanagement.etudiants.model.EmploiTempsId;

@Repository
public interface SallesRepository extends JpaRepository<Salle,String>{
	@Query("""
	        SELECT 
	            e.noSalle
	        
	        FROM Salle e
	    """)
  List<String> findAllSalles();

}

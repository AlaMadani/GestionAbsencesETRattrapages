package tn.enicarthage.absencemanagement.administration.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.enicarthage.absencemanagement.administration.model.SeanceInfo;
import tn.enicarthage.absencemanagement.enseignants.model.Aceptee;
import tn.enicarthage.absencemanagement.enseignants.model.Rattrappage;
import tn.enicarthage.absencemanagement.etudiants.model.Seance;

@Repository
public interface SeanceRepository extends JpaRepository<Seance, Integer>{
	@Query("""
		      SELECT new tn.enicarthage.absencemanagement.administration.model.SeanceInfo(
		          s.ISrattrappage,
		          e.id,
		          sal.noSalle
		        
		      )
		      FROM Seance s
		      JOIN s.enseignant e
		      JOIN s.salle sal
		      WHERE s.id = :ids
		    """)
		   SeanceInfo IsRattrapage(
		    		@Param("ids") int id
		    		
		    		);
}

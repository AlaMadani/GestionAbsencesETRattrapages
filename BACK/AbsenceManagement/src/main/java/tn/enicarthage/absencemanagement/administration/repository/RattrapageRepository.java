package tn.enicarthage.absencemanagement.administration.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.enicarthage.absencemanagement.administration.model.AbsenceDTO;
import tn.enicarthage.absencemanagement.administration.model.ProcessedRattrapageDTO;
import tn.enicarthage.absencemanagement.administration.model.RattrapageDTO;
import tn.enicarthage.absencemanagement.enseignants.model.Absence;
import tn.enicarthage.absencemanagement.enseignants.model.Rattrappage;
import tn.enicarthage.absencemanagement.etudiants.model.Classe;
@Repository
public interface RattrapageRepository extends JpaRepository<Rattrappage, Long>{
	
	@Query("""
		      SELECT new tn.enicarthage.absencemanagement.administration.model.RattrapageDTO(
		        a.id,
		        a.date_db,           
		        a.date_fin,
		        a.seancedb,
		        a.seancefin,
		        a.acceptee,
		        a.classe,
		        a.specialite,
		        a.groupe,
		        e.id,           
		        e.nom,
		        e.prenom,
		        e.grade,
		        e.num_tel,
		        e.nbAbsences
		      )
		      FROM Rattrappage a
		      JOIN a.enseignant e
		      WHERE a.acceptee IS NULL
		    """)
		    List<RattrapageDTO> findAllWithEnseignant();
	
	
	@Query("""
		      SELECT 
		          r.date_aff
		        
		      
		      FROM Seance s
		      JOIN s.rattrapage r
		      WHERE s.id = :ids
		    """)
		   LocalDate findDateAffRattById(
		    		@Param("ids") int id
		    		
		    		);
	
	List<Rattrappage> findByEnseignant_Id(Long enseignantId);
	
	
	
	
	@Query("""
		      SELECT new tn.enicarthage.absencemanagement.administration.model.ProcessedRattrapageDTO(
		        a.id,
		        a.date_db,           
		        a.date_fin,
		        a.seancedb,
		        a.seancefin,
		        a.acceptee,
		        a.classe,
		        a.specialite,
		        a.groupe,
		        e.id,           
		        e.nom,
		        e.prenom,
		        e.grade,
		        e.num_tel,
		        e.nbAbsences,
		        a.date_aff,
		        a.seanceAff,
		        a.salle.noSalle
		      )
		      FROM Rattrappage a
		      JOIN a.enseignant e
		      WHERE a.acceptee IS NOT NULL
		    """)
		    List<ProcessedRattrapageDTO> findAllProcessedWithEnseignant();
	
	
	
	
	
	
	
	@Query("""
		      SELECT new tn.enicarthage.absencemanagement.administration.model.ProcessedRattrapageDTO(
		        a.id,
		        a.date_db,           
		        a.date_fin,
		        a.seancedb,
		        a.seancefin,
		        a.acceptee,
		        a.classe,
		        a.specialite,
		        a.groupe,
		        e.id,           
		        e.nom,
		        e.prenom,
		        e.grade,
		        e.num_tel,
		        e.nbAbsences,
		        a.date_aff,
		        a.seanceAff,
		        a.salle.noSalle
		      )
		      FROM Rattrappage a
		      JOIN a.enseignant e
		      WHERE a.acceptee =Aceptee.oui
		    """)
		    List<ProcessedRattrapageDTO> findAllAcceptedRatt();

}

package tn.enicarthage.absencemanagement.administration.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.enicarthage.absencemanagement.administration.model.AbsenceDTO;
import tn.enicarthage.absencemanagement.administration.model.EmploiTempsRowsDTO;
import tn.enicarthage.absencemanagement.administration.model.RattrapageDTO;
import tn.enicarthage.absencemanagement.enseignants.model.Absence;
import tn.enicarthage.absencemanagement.etudiants.model.Classe;
import tn.enicarthage.absencemanagement.etudiants.model.EmploiTemps;
import tn.enicarthage.absencemanagement.etudiants.model.EmploiTempsId;
import tn.enicarthage.absencemanagement.etudiants.model.Groupe;
import tn.enicarthage.absencemanagement.etudiants.model.Specialite;
@Repository
public interface EmploiTempsRepository extends JpaRepository<EmploiTemps,EmploiTempsId>{
	
	  @Query("""
		        SELECT new tn.enicarthage.absencemanagement.administration.model.EmploiTempsRowsDTO(
		            e.id.jourr,
		            e.id.classe,
		            e.id.specialite,
		            e.id.groupe,
		            e.seances.seance1.id,
		            e.seances.seance2.id,
		            e.seances.seance3.id,
		            e.seances.seance4.id,
		            e.seances.seance5.id
		        )
		        FROM EmploiTemps e
		        WHERE e.id.classe = :classe
		          AND e.id.specialite = :specialite
		          AND e.id.groupe = :groupe
		    """)
		    List<EmploiTempsRowsDTO> findRowsByClasseSpecialiteGroupe(
		        @Param("classe") Classe classe,
		        @Param("specialite") Specialite specialite,
		        @Param("groupe") Groupe groupe
		    );
	  
	  @Query("""
		        SELECT new tn.enicarthage.absencemanagement.administration.model.EmploiTempsRowsDTO(
		            e.id.jourr,
		            e.id.classe,
		            e.id.specialite,
		            e.id.groupe,
		            e.seances.seance1.id,
		            e.seances.seance2.id,
		            e.seances.seance3.id,
		            e.seances.seance4.id,
		            e.seances.seance5.id
		        )
		        FROM EmploiTemps e
		    """)
	  List<EmploiTempsRowsDTO> findAllRows();
	  
	  
	  @Query("SELECT DISTINCT e.id.classe FROM EmploiTemps e")
	    List<Classe> findAllClasses();

	    @Query("SELECT DISTINCT e.id.specialite FROM EmploiTemps e WHERE e.id.classe = :classe")
	    List<Specialite> findSpecialitesByClasse(@Param("classe") Classe classe);

	    @Query("SELECT DISTINCT e.id.groupe FROM EmploiTemps e WHERE e.id.classe = :classe AND e.id.specialite = :specialite")
	    List<Groupe> findGroupesByClasseAndSpecialite(@Param("classe") Classe classe, @Param("specialite") Specialite specialite);

}

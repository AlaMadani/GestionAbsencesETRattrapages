package tn.enicarthage.absencemanagement.administration.service;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import tn.enicarthage.absencemanagement.administration.model.AbsenceDTO;
import tn.enicarthage.absencemanagement.administration.model.ProcessedRattrapageDTO;
import tn.enicarthage.absencemanagement.administration.model.RattrapageDTO;
import tn.enicarthage.absencemanagement.administration.model.Salle;
import tn.enicarthage.absencemanagement.administration.repository.AbsenceRepository;
import tn.enicarthage.absencemanagement.administration.repository.EmploiTempsRepository;
import tn.enicarthage.absencemanagement.administration.repository.EnseignantRepository;
import tn.enicarthage.absencemanagement.administration.repository.RattrapageRepository;
import tn.enicarthage.absencemanagement.administration.repository.SallesRepository;
import tn.enicarthage.absencemanagement.administration.repository.SeanceRepository;
import tn.enicarthage.absencemanagement.enseignants.model.Aceptee;
import tn.enicarthage.absencemanagement.enseignants.model.Enseignant;
import tn.enicarthage.absencemanagement.enseignants.model.NewRattrapageRequest;
import tn.enicarthage.absencemanagement.enseignants.model.RattrapageDTOENS;
import tn.enicarthage.absencemanagement.enseignants.model.Rattrappage;
import tn.enicarthage.absencemanagement.enseignants.model.numSeance;
import tn.enicarthage.absencemanagement.etudiants.model.Classe;
import tn.enicarthage.absencemanagement.etudiants.model.EmploiTemps;
import tn.enicarthage.absencemanagement.etudiants.model.EmploiTempsId;
import tn.enicarthage.absencemanagement.etudiants.model.Etudiant;
import tn.enicarthage.absencemanagement.etudiants.model.Groupe;
import tn.enicarthage.absencemanagement.etudiants.model.Seance;
import tn.enicarthage.absencemanagement.etudiants.model.Seances;
import tn.enicarthage.absencemanagement.etudiants.model.Specialite;
import tn.enicarthage.absencemanagement.etudiants.model.jour;
@AllArgsConstructor

@Service
public class RattrapageService {
	@Autowired
	private final RattrapageRepository rattrapageRepository;
	 private final SallesRepository          salleRepo;
	    private final EnseignantRepository     enseignantRepo;
	    private final SeanceRepository         seanceRepo;
	    private final EmploiTempsRepository emploiTempsRepo;

	 public List<RattrapageDTO> getAllRattrapages() {
	        return rattrapageRepository.findAllWithEnseignant();
	    }
	 public LocalDate getDateAffRatt(int ids) {
	        return rattrapageRepository.findDateAffRattById( ids);
	    }
	 
	 public List<ProcessedRattrapageDTO> getAllProcessedRattrapages() {
			return rattrapageRepository.findAllProcessedWithEnseignant();
		}
	 
	 public List<ProcessedRattrapageDTO> getAllAcceptedRatt() {
			return rattrapageRepository.findAllAcceptedRatt();
		}
	 
	
     
	 @Transactional
	    public void accepterEtCreerSeance(
	            Long rattrId,
	            LocalDate dateAff,
	            numSeance seanceAff,
	            String salleId,
	            Long enseignantId
	    ) {
	        Rattrappage ratt = rattrapageRepository.findById(rattrId)
	            .orElseThrow(() -> new IllegalArgumentException("Rattrappage introuvable : " + rattrId));

	        Salle salle = salleRepo.findById(salleId)
	            .orElseThrow(() -> new IllegalArgumentException("Salle introuvable : " + salleId));

	        Enseignant ens = enseignantRepo.findById(enseignantId)
	            .orElseThrow(() -> new IllegalArgumentException("Enseignant introuvable : " + enseignantId));

	        // 1) Mettre à jour le Rattrappage
	        ratt.setAcceptee(Aceptee.oui);
	        ratt.setDate_aff(dateAff);
	        ratt.setSeanceAff(seanceAff);
	        ratt.setSalle(salle);
	        ratt.setEnseignant(ens);
	        rattrapageRepository.save(ratt);

	        // 2) Créer et persister la nouvelle Seance de rattrapage
	        Seance seance = new Seance();
	        seance.setMatiere(ratt.getMatiere());
	        seance.setISrattrappage(Aceptee.oui);
	        seance.setEnseignant(ens);
	        seance.setRattrapage(ratt);
	        seance.setSalle(salle);
	        seanceRepo.save(seance);
	        
	        
	        
	        
	        
	        
	        // 1) Calculer le jour de semaine en votre enum `jour`
	        DayOfWeek dow = dateAff.getDayOfWeek();
	        jour j = switch (dow) {
	            case MONDAY    -> jour.LUNDI;
	            case TUESDAY   -> jour.MARDI;
	            case WEDNESDAY -> jour.MERCREDI;
	            case THURSDAY  -> jour.JEUDI;
	            case FRIDAY    -> jour.VENDREDI;
	            case SATURDAY  -> jour.SAMEDI;
	            default -> throw new IllegalStateException("Pas de cours le dimanche");
	        };

	        // 2) Construire la clé composite
	        EmploiTempsId etId = new EmploiTempsId(
	            j,
	            ratt.getClasse(),
	            ratt.getSpecialite(),
	            ratt.getGroupe()
	        );

	        // 3) Charger l’EmploiTemps (ou lever si absent)
	        EmploiTemps et = emploiTempsRepo.findById(etId)
	            .orElseThrow(() ->
	              new IllegalArgumentException("EmploiTemps manquant pour " + etId)
	            );

	        // 4) Mettre dans le bon créneau
	        Seances ses = et.getSeances();
	        switch (seanceAff) {
	            case S1 -> ses.setSeance1(seance);
	            case S2 -> ses.setSeance2(seance);
	            case S3 -> ses.setSeance3(seance);
	            case S4 -> ses.setSeance4(seance);
	            case S5 -> ses.setSeance5(seance);
	        }
	        et.setSeances(ses);

	        // 5) Sauvegarde
	        emploiTempsRepo.save(et);
	    }
	 
	 
	  @Transactional
	    public void rejeterRattrapage(Long rattrapageId) {
	        Rattrappage ratt = rattrapageRepository.findById(rattrapageId)
	            .orElseThrow(() -> new IllegalArgumentException(
	                "Rattrapage introuvable : " + rattrapageId));

	        ratt.setAcceptee(Aceptee.non);
	        // optionnel : on peut aussi nettoyer date_aff et seanceAff si on veut
	        // ratt.setDate_aff(null);
	        // ratt.setSeanceAff(null);

	        rattrapageRepository.save(ratt);
	    }
	  
	  
	  
	  @Transactional
	    public Long createRattrapage(NewRattrapageRequest req) {
	        var ratt = new Rattrappage();

	        // clé auto-générée
	        // ratt.setId(...) — Hibernate fera l’ID

	        // dates de début / fin en java.sql.Date
	        ratt.setDate_db(Date.valueOf(req.getDateDebut()));
	        ratt.setDate_fin(Date.valueOf(req.getDateFin()));

	        // on ne renseigne pas encore bennéficiaires
	        ratt.setSeancedb(null);
	        ratt.setSeancefin(null);

	        ratt.setMatiere(req.getMatiere());
	        // flagged non par défaut
	        ratt.setAcceptee(null);
	        ratt.setPinned(Aceptee.non);

	        // contexte cours
	        ratt.setClasse(req.getClasse());
	        ratt.setSpecialite(req.getSpecialite());
	        ratt.setGroupe(req.getGroupe());

	        // pas encore affecté
	        ratt.setDate_aff(null);
	        ratt.setSeanceAff(null);

	        // lien enseignant obligatoire
	        var ens = enseignantRepo.findById(req.getEnseignantId())
	            .orElseThrow(() -> new IllegalArgumentException(
	                "Enseignant introuvable : " + req.getEnseignantId()
	            ));
	        ratt.setEnseignant(ens);

	        // pas de salle encore
	        ratt.setSalle(null);

	        // persister
	        Rattrappage saved = rattrapageRepository.save(ratt);
	        return saved.getId();
	    }
	  
	  
	  public List<RattrapageDTOENS> getByEnseignant(Long enseignantId) {
	        return rattrapageRepository.findByEnseignant_Id(enseignantId).stream()
	            .map(this::toDto)
	            .collect(Collectors.toList());
	    }

	    private RattrapageDTOENS toDto(Rattrappage r) {
	        return new RattrapageDTOENS(
	            r.getId(),
	            r.getDate_db().toLocalDate(),
	            r.getDate_fin().toLocalDate(),
	            r.getSeancedb(),
	            r.getSeancefin(),
	            r.getAcceptee() != null ? r.getAcceptee().name() : null,
	            r.getDate_aff(),
	            r.getSeanceAff(),
	            r.getPinned()   != null ? r.getPinned().name()   : null,
	            r.getClasse(),
	            r.getSpecialite(),
	            r.getGroupe(),
	            r.getSalle() != null ? r.getSalle().getNoSalle() : null
	        );
	    }
	 
	 

}

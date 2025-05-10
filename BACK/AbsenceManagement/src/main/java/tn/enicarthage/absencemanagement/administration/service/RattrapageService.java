package tn.enicarthage.absencemanagement.administration.service;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
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
import tn.enicarthage.absencemanagement.etudiants.model.Seance;
import tn.enicarthage.absencemanagement.etudiants.model.Seances;
import tn.enicarthage.absencemanagement.etudiants.model.Specialite;
import tn.enicarthage.absencemanagement.etudiants.model.Groupe;
import tn.enicarthage.absencemanagement.etudiants.model.jour;

@AllArgsConstructor
@Service
public class RattrapageService {

    @Autowired
    private final RattrapageRepository rattrapageRepository;
    @Autowired
    private final SallesRepository salleRepo;
    @Autowired
    private final EnseignantRepository enseignantRepo;
    @Autowired
    private final SeanceRepository seanceRepo;
    @Autowired
    private final EmploiTempsRepository emploiTempsRepo;
    @Autowired
    private JavaMailSender mailSender;

    public List<RattrapageDTO> getAllRattrapages() {
        return rattrapageRepository.findAllWithEnseignant();
    }

    public LocalDate getDateAffRatt(int ids) {
        return rattrapageRepository.findDateAffRattById(ids);
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
        // Fetch Rattrappage
        Rattrappage ratt = rattrapageRepository.findById(rattrId)
                .orElseThrow(() -> new IllegalArgumentException("Rattrappage introuvable : " + rattrId));

        // Fetch Salle
        Salle salle = salleRepo.findById(salleId)
                .orElseThrow(() -> new IllegalArgumentException("Salle introuvable : " + salleId));

        // Fetch Enseignant
        Enseignant ens = enseignantRepo.findById(enseignantId)
                .orElseThrow(() -> new IllegalArgumentException("Enseignant introuvable : " + enseignantId));

        // 1) Update Rattrappage
        ratt.setAcceptee(Aceptee.oui);
        ratt.setDate_aff(dateAff);
        ratt.setSeanceAff(seanceAff);
        ratt.setSalle(salle);
        ratt.setEnseignant(ens);
        rattrapageRepository.save(ratt);

        // 2) Create and persist new Seance
        Seance seance = new Seance();
        seance.setMatiere(ratt.getMatiere());
        seance.setISrattrappage(Aceptee.oui);
        seance.setEnseignant(ens);
        seance.setRattrapage(ratt);
        seance.setSalle(salle);
        seanceRepo.save(seance);

        // 3) Calculate day of week
        DayOfWeek dow = dateAff.getDayOfWeek();
        jour j = switch (dow) {
            case MONDAY -> jour.LUNDI;
            case TUESDAY -> jour.MARDI;
            case WEDNESDAY -> jour.MERCREDI;
            case THURSDAY -> jour.JEUDI;
            case FRIDAY -> jour.VENDREDI;
            case SATURDAY -> jour.SAMEDI;
            default -> throw new IllegalStateException("Pas de cours le dimanche");
        };

        // 4) Build composite key for EmploiTemps
        EmploiTempsId etId = new EmploiTempsId(
                j,
                ratt.getClasse(),
                ratt.getSpecialite(),
                ratt.getGroupe()
        );

        // 5) Load EmploiTemps
        EmploiTemps et = emploiTempsRepo.findById(etId)
                .orElseThrow(() ->
                        new IllegalArgumentException("EmploiTemps manquant pour " + etId)
                );

        // 6) Assign Seance to the correct slot
        Seances ses = et.getSeances();
        switch (seanceAff) {
            case S1 -> ses.setSeance1(seance);
            case S2 -> ses.setSeance2(seance);
            case S3 -> ses.setSeance3(seance);
            case S4 -> ses.setSeance4(seance);
            case S5 -> ses.setSeance5(seance);
        }
        et.setSeances(ses);

        // 7) Save EmploiTemps
        emploiTempsRepo.save(et);

        // 8) Send confirmation email
        sendRattrapageConfirmation(ratt, dateAff, seanceAff, salle, ens);
    }

    private void sendRattrapageConfirmation(Rattrappage ratt, LocalDate dateAff, numSeance seanceAff, Salle salle, Enseignant ens) {
        // Construct email address
        String specialite = ratt.getSpecialite().name().toLowerCase();
        String classe = switch (ratt.getClasse()) {
            case PREMIERE -> "1";
            case DEUXIEME -> "2";
            case TROISIEME -> "3";
            default -> throw new IllegalArgumentException("Classe non supportée : " + ratt.getClasse());
        };
        String groupe = ratt.getGroupe().name().toLowerCase();
        String to = "alemedeni0@gmail.com";

        // Create email
        MimeMessage msg = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(msg, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject("Confirmation de séance de rattrapage");
            String body = new StringBuilder()
                    .append("Bonjour,<br><br>")
                    .append("Une séance de rattrapage a été <b>confirmée</b> pour votre groupe :<br>")
                    .append("• Matière : ").append(ratt.getMatiere()).append("<br>")
                    .append("• Date : ").append(dateAff).append("<br>")
                    .append("• Créneau : ").append(seanceAff.name()).append("<br>")
                    .append("• Salle : ").append(salle.getNoSalle()).append("<br>")
                    .append("• Enseignant : ").append(ens.getNom()).append(" ").append(ens.getPrenom()).append("<br><br>")
                    .append("Merci de vous présenter à l'heure. Pour toute question, contactez l'administration.<br><br>")
                    .append("Cordialement,<br>")
                    .append("L'administration ENI Carthage")
                    .toString();
            helper.setText(body, true);
            mailSender.send(msg);
        } catch (MessagingException e) {
            throw new IllegalStateException("Erreur lors de l’envoi de l’email de confirmation", e);
        }
    }

    @Transactional
    public void rejeterRattrapage(Long rattrapageId) {
        Rattrappage ratt = rattrapageRepository.findById(rattrapageId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Rattrapage introuvable : " + rattrapageId));

        ratt.setAcceptee(Aceptee.non);
        rattrapageRepository.save(ratt);
    }

    @Transactional
    public Long createRattrapage(NewRattrapageRequest req) {
        var ratt = new Rattrappage();
        ratt.setDate_db(Date.valueOf(req.getDateDebut()));
        ratt.setDate_fin(Date.valueOf(req.getDateFin()));
        ratt.setSeancedb(null);
        ratt.setSeancefin(null);
        ratt.setMatiere(req.getMatiere());
        ratt.setAcceptee(null);
        ratt.setPinned(Aceptee.non);
        ratt.setClasse(req.getClasse());
        ratt.setSpecialite(req.getSpecialite());
        ratt.setGroupe(req.getGroupe());
        ratt.setDate_aff(null);
        ratt.setSeanceAff(null);
        var ens = enseignantRepo.findById(req.getEnseignantId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Enseignant introuvable : " + req.getEnseignantId()
                ));
        ratt.setEnseignant(ens);
        ratt.setSalle(null);
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
                r.getPinned() != null ? r.getPinned().name() : null,
                r.getClasse(),
                r.getSpecialite(),
                r.getGroupe(),
                r.getSalle() != null ? r.getSalle().getNoSalle() : null
        );
    }
}
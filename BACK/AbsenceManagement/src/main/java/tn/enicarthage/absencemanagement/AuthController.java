package tn.enicarthage.absencemanagement;

import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.enicarthage.absencemanagement.administration.service.EnseignantService;

import tn.enicarthage.absencemanagement.administration.service.StudentService;
import tn.enicarthage.absencemanagement.administration.model.EnseignantDTO;
import tn.enicarthage.absencemanagement.administration.model.EtudiantDTO;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RequestMapping("/api/auth")
public class AuthController {

    private final EnseignantService enseignantService;
    private final StudentService etudiantService;

    public AuthController(EnseignantService enseignantService,  StudentService etudiantService) {
        this.enseignantService = enseignantService;
        this.etudiantService = etudiantService;
    }

    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> me(Authentication auth) {
        if (auth == null || !(auth.getPrincipal() instanceof UserDetails)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        UserDetails user = (UserDetails) auth.getPrincipal();
        List<String> roles = user.getAuthorities().stream()
                               .map(GrantedAuthority::getAuthority)
                               .toList();

        Long id = null;
        if (user instanceof AppUser appUser) {
            id = appUser.getId();
        } else {
            // Fallback for specific roles
            if (roles.contains("ROLE_ENSEIGNANT")) {
                EnseignantDTO enseignant = enseignantService.findByEmail(user.getUsername());
                if (enseignant != null) {
                    id = enseignant.getId();
                }
            } else if (roles.contains("ROLE_ETUDIANT")) {
                EtudiantDTO etudiant = etudiantService.findByEmail(user.getUsername());
                if (etudiant != null) {
                    id = etudiant.getId();
                }
            }
        }

        if (id == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(Map.of("error", "User ID not found"));
        }

        return ResponseEntity.ok(Map.of(
            "roles", roles,
            "id", id
        ));
    }
}
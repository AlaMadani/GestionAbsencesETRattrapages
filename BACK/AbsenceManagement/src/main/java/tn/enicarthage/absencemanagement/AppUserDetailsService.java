package tn.enicarthage.absencemanagement;

import java.util.Arrays;
import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tn.enicarthage.absencemanagement.etudiants.model.User;

@Service
public class AppUserDetailsService implements UserDetailsService {
    private final UserRepository userRepo;

    public AppUserDetailsService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé : " + email));
        
        List<SimpleGrantedAuthority> authorities = Arrays.stream(detectRoles(user))
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .toList();
        
        return new AppUser(
                user.getId(),
                user.getEmail(),
                user.getMotdepass(),
                authorities
        );
    }

    private String[] detectRoles(User user) {
        if (user instanceof tn.enicarthage.absencemanagement.administration.model.Admin) {
            return new String[] {"ADMIN"};
        }
        if (user instanceof tn.enicarthage.absencemanagement.etudiants.model.Etudiant) {
            return new String[] {"ETUDIANT"};
        }
        if (user instanceof tn.enicarthage.absencemanagement.enseignants.model.Enseignant) {
            return new String[] {"ENSEIGNANT"};
        }
        return new String[] {"USER"};
    }
}
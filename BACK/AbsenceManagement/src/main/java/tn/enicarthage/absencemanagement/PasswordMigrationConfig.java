package tn.enicarthage.absencemanagement;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import tn.enicarthage.absencemanagement.etudiants.model.User;

@Configuration
public class PasswordMigrationConfig {

    @Bean
    public CommandLineRunner hashAllPasswords(
            UserRepository userRepo,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            List<User> users = userRepo.findAll();
            for (User u : users) {
                String raw = u.getMotdepass();
                // Très basique : tous les hashes BCrypt commencent par "$2a$" ou "$2b$"
                if (raw == null || raw.startsWith("$2a$") || raw.startsWith("$2b$")) {
                    continue; // déjà hashé ou null
                }
                String hashed = passwordEncoder.encode(raw);
                u.setMotdepass(hashed);
                userRepo.save(u);
                System.out.printf("Hashed password for user %s (id=%d)%n",
                                  u.getEmail(), u.getId());
            }
        };
    }
}

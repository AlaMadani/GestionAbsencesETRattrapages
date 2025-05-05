package tn.enicarthage.absencemanagement;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.enicarthage.absencemanagement.etudiants.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}

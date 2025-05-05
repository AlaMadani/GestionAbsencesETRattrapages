package tn.enicarthage.absencemanagement.administration.service;


import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.enicarthage.absencemanagement.etudiants.model.Etudiant;
import tn.enicarthage.absencemanagement.etudiants.model.User;
import tn.enicarthage.absencemanagement.enseignants.model.Enseignant;
import tn.enicarthage.absencemanagement.AppUser;
import tn.enicarthage.absencemanagement.UserRepository;
import tn.enicarthage.absencemanagement.administration.model.Admin; // Assumed package
import tn.enicarthage.absencemanagement.administration.model.SignupRequest;

import jakarta.persistence.EntityExistsException;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class UserService {

 private final UserRepository userRepository;
 private final PasswordEncoder passwordEncoder;

 public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
     this.userRepository = userRepository;
     this.passwordEncoder = passwordEncoder;
 }

 public User registerUser(SignupRequest signupRequest) {
     // Manual validation
     if (signupRequest.getEmail() == null || signupRequest.getEmail().trim().isEmpty()) {
         throw new IllegalArgumentException("Email is required");
     }
     if (!isValidEmail(signupRequest.getEmail())) {
         throw new IllegalArgumentException("Invalid email format");
     }
     if (signupRequest.getPassword() == null || signupRequest.getPassword().trim().isEmpty()) {
         throw new IllegalArgumentException("Password is required");
     }
     if (signupRequest.getNom() == null || signupRequest.getNom().trim().isEmpty()) {
         throw new IllegalArgumentException("Name is required");
     }
     if (signupRequest.getPrenom() == null || signupRequest.getPrenom().trim().isEmpty()) {
         throw new IllegalArgumentException("First name is required");
     }
     if (signupRequest.getUserType() == null) {
         throw new IllegalArgumentException("User type is required");
     }

     // Check for duplicate email
     if (userRepository.findByEmail(signupRequest.getEmail()).isPresent()) {
         throw new EntityExistsException("Email already registered: " + signupRequest.getEmail());
     }

     // Create the appropriate user type
     User user;
     switch (signupRequest.getUserType()) {
         case ETUDIANT:
             Etudiant etudiant = new Etudiant();
             etudiant.setNom(signupRequest.getNom());
             etudiant.setPrenom(signupRequest.getPrenom());
             etudiant.setEmail(signupRequest.getEmail());
             user = etudiant;
             break;
         case ENSEIGNANT:
             Enseignant enseignant = new Enseignant();
             enseignant.setNom(signupRequest.getNom());
             enseignant.setPrenom(signupRequest.getPrenom());
             enseignant.setEmail(signupRequest.getEmail());
             enseignant.setGrade(signupRequest.getGrade());
             enseignant.setNum_tel(signupRequest.getNumTel());
             user = enseignant;
             break;
         case ADMIN:
             Admin admin = new Admin(); // Assumed constructor
             admin.setNom(signupRequest.getNom());
             admin.setPrenom(signupRequest.getPrenom());
             admin.setEmail(signupRequest.getEmail());
             user = admin;
             break;
         default:
             throw new IllegalArgumentException("Unknown user type: " + signupRequest.getUserType());
     }

     // Hash the password
     String hashedPassword = passwordEncoder.encode(signupRequest.getPassword());
     user.setMotdepass(hashedPassword);
     
  // Create AppUser for security context (example)
     List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + signupRequest.getUserType().name()));
     AppUser appUser = new AppUser(
         user.getId(), // Assuming User has an ID after save
         signupRequest.getEmail(),
         hashedPassword,
         signupRequest.getNom(),
         signupRequest.getPrenom(),
         authorities
     );

     // Save the user
     return userRepository.save(user);
 }

 private boolean isValidEmail(String email) {
     String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
     Pattern pattern = Pattern.compile(emailRegex);
     return email != null && pattern.matcher(email).matches();
 }
}
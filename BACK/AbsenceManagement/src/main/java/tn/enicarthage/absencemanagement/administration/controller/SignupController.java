package tn.enicarthage.absencemanagement.administration.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityExistsException;
import tn.enicarthage.absencemanagement.administration.model.SignupRequest;
import tn.enicarthage.absencemanagement.administration.service.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RequestMapping("/api/auth")
public class SignupController {

 private final UserService userService;

 public SignupController(UserService userService) {
     this.userService = userService;
 }

 @PostMapping("/signup")
 public ResponseEntity<String> signup(@RequestBody SignupRequest signupRequest) {
     try {
         userService.registerUser(signupRequest);
         return ResponseEntity.ok("User registered successfully");
     } catch (IllegalArgumentException e) {
         return ResponseEntity.badRequest().body(e.getMessage());
     } catch (EntityExistsException e) {
         return ResponseEntity.badRequest().body(e.getMessage());
     } catch (Exception e) {
         return ResponseEntity.status(500).body("Error registering user: " + e.getMessage());
     }
 }
}
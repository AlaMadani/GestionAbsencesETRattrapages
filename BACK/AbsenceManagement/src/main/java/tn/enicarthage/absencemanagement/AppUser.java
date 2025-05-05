package tn.enicarthage.absencemanagement;

import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class AppUser implements UserDetails {
    private Long id; // User ID (e.g., enseignantId)
    private String username;
    private String password;
    private String nom; // Added
    private String prenom; // Added
    private List<SimpleGrantedAuthority> authorities;

    // Constructor
    public AppUser(Long id, String username, String password, String nom, String prenom, List<SimpleGrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.authorities = authorities;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getNom() { // Added getter
        return nom;
    }

    public String getPrenom() { // Added getter
        return prenom;
    }

    public void setNom(String nom) { // Added setter
        this.nom = nom;
    }

    public void setPrenom(String prenom) { // Added setter
        this.prenom = prenom;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
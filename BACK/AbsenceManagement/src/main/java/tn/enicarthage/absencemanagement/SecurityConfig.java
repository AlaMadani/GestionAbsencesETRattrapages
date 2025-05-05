package tn.enicarthage.absencemanagement;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private final AppUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
          .cors().and()
          .csrf().disable()

          .exceptionHandling(ex -> ex
            .authenticationEntryPoint(
              (req, res, authException) -> res.sendError(HttpStatus.UNAUTHORIZED.value())
            )
          )

          .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/auth/**").permitAll()
            .requestMatchers("/api/admin/**").hasRole("ADMIN")
            .requestMatchers("/api/enseignant/**").hasRole("ENSEIGNANT")
            .requestMatchers("/api/etudiant/**").hasRole("ETUDIANT")
            .anyRequest().authenticated()
          )

          .formLogin(form -> form
            .loginPage("/api/auth/login")
            .loginProcessingUrl("/api/auth/login")
            .successHandler((req, res, auth) -> res.setStatus(HttpStatus.OK.value()))
            .failureHandler((req, res, ex) -> res.sendError(HttpStatus.UNAUTHORIZED.value()))
            .permitAll()
          )

          .logout(logout -> logout
            .logoutUrl("/api/auth/logout")
            .logoutSuccessHandler((req, res, auth) -> res.setStatus(HttpStatus.OK.value()))
            .permitAll()
          )

          .userDetailsService(userDetailsService)
          .authenticationProvider(daoAuthProvider());

        return http.build();
    }


    @Bean
    public DaoAuthenticationProvider daoAuthProvider() {
        var provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cfg = new CorsConfiguration();
        cfg.setAllowedOrigins(List.of("http://localhost:4200"));
        cfg.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
        cfg.setAllowedHeaders(List.of("*"));
        cfg.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource src = new UrlBasedCorsConfigurationSource();
        src.registerCorsConfiguration("/**", cfg);
        return src;
    }
}

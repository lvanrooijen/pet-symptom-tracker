package com.laila.pet_symptom_tracker.securityconfig;

import com.laila.pet_symptom_tracker.entities.authentication.BasicAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {
  private final BasicAuthenticationProvider basicAuthProvider;

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    // TODO endpoints goed configureren
    http.authenticationProvider(basicAuthProvider)
        .authorizeHttpRequests(c -> c.anyRequest().hasAuthority("READ_ALL_PETS"))
        .httpBasic(Customizer.withDefaults());

    return http.build();
  }
}

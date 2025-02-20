package com.laila.pet_symptom_tracker.securityconfig;

import com.laila.pet_symptom_tracker.entities.authentication.SecurityUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class SecurityConfiguration {
  @Bean
  UserDetailsService userDetailsService(SecurityUserService securityUserService) {
    return securityUserService;
  }
}

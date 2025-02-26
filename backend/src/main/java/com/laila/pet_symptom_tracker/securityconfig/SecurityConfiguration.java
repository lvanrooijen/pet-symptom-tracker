package com.laila.pet_symptom_tracker.securityconfig;

import java.util.logging.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration {
  private final Logger log = Logger.getLogger(SecurityConfiguration.class.getName());
  private final JwtAuthFilter jwtAuthFilter;
  private final CorsConfig corsConfig;

  public SecurityConfiguration(JwtAuthFilter jwtAuthFilter, CorsConfig corsConfig) {
    this.jwtAuthFilter = jwtAuthFilter;
    this.corsConfig = corsConfig;
  }

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .cors(cors -> cors.configurationSource(corsConfig.corsConfiguration()))
        .httpBasic(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(
            request ->
                request
                    .requestMatchers(HttpMethod.POST, "/api/v1/login", "/api/v1/register")
                    .permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/v1/pet-types")
                    .authenticated()
                    .anyRequest()
                    .authenticated());

    http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

    http.exceptionHandling(
        configures -> configures.authenticationEntryPoint(new NotAuthorizedEntryPoint()));

    return http.build();
  }
}

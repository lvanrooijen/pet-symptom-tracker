package com.laila.pet_symptom_tracker.securityconfig;

import com.laila.pet_symptom_tracker.mainconfig.SecurityPaths;
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
                    // Open to all paths
                    .requestMatchers(HttpMethod.POST, SecurityPaths.OPEN_POST_PATHS)
                    .permitAll()
                    // Get Paths
                    .requestMatchers(HttpMethod.GET, SecurityPaths.USER_GET_PATHS)
                    .authenticated()
                    // Post paths
                    .requestMatchers(HttpMethod.POST, SecurityPaths.ADMIN_MODERATOR_PATHS)
                    .hasAnyRole("ADMIN", "MODERATOR")
                    // Patch paths
                    .requestMatchers(HttpMethod.PATCH, SecurityPaths.ADMIN_MODERATOR_PATHS)
                    .hasAnyRole("ADMIN", "MODERATOR")
                    // Delete Paths
                    .requestMatchers(HttpMethod.DELETE, SecurityPaths.ADMIN_MODERATOR_PATHS)
                    .hasAnyRole("ADMIN", "MODERATOR")
                    // Remainder paths
                    .anyRequest()
                    .authenticated());

    http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

    http.exceptionHandling(
        configures -> configures.authenticationEntryPoint(new NotAuthorizedEntryPoint()));

    return http.build();
  }
}

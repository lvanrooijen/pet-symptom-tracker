package com.laila.pet_symptom_tracker.securityconfig;

import com.laila.pet_symptom_tracker.entities.authentication.BasicAuthenticationProvider;
import com.laila.pet_symptom_tracker.mainconfig.TerminalColors;
import com.laila.pet_symptom_tracker.temporary.CsrfTokenLogger;
import java.util.List;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class SecurityConfiguration {
  private final BasicAuthenticationProvider basicAuthProvider;
  private final Logger log = Logger.getLogger(SecurityConfiguration.class.getName());
  private String corsClient;

  public SecurityConfiguration(
      BasicAuthenticationProvider basicAuthProvider,
      @Value("${pet_symptom_tracker.cors}") String corsClient) {
    this.corsClient = corsClient;
    this.basicAuthProvider = basicAuthProvider;
  }

  // Configures CORS
  @Bean
  CorsConfigurationSource corsConfiguration() {
    log.info(TerminalColors.setWarningColor(corsClient));
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(List.of(corsClient));
    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    configuration.setAllowedHeaders(
        List.of("Authorization", "Cache-Control", "Content-Type", "Accept", "X-Requested-With"));
    configuration.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  // Registers the CORS filter with the highest priority, so it is processed before others
  @Bean
  FilterRegistrationBean<CorsFilter> corsFilterBean() {

    FilterRegistrationBean<CorsFilter> bean =
        new FilterRegistrationBean<>(new CorsFilter(corsConfiguration()));
    bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
    return bean;
  }

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.addFilterAfter(new CsrfTokenLogger(), CsrfFilter.class)
        .authenticationProvider(basicAuthProvider)
        .authorizeHttpRequests(c -> c.anyRequest().permitAll())
        .httpBasic(Customizer.withDefaults());

    return http.build();
  }
}

package com.laila.pet_symptom_tracker.securityconfig;

import com.laila.pet_symptom_tracker.mainconfig.Routes;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletResponse;
import javax.crypto.SecretKey;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration {
  @Bean
  public SecretKey secretKey() {
    return Jwts.SIG.HS256.key().build();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(
      HttpSecurity httpSecurity, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
    return httpSecurity
        .csrf(AbstractHttpConfigurer::disable)
        .httpBasic(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(
            requests ->
                requests
                    .requestMatchers(Routes.AUTHENTICATION + "/**")
                    .permitAll()
                    .requestMatchers(Routes.BASE_ROUTE + "/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated())
            .logout(logout-> logout.logoutUrl(Routes.AUTHENTICATION)
                    .logoutSuccessHandler((request,response,authentication) -> response.setStatus(HttpServletResponse.SC_OK)))
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .exceptionHandling(
            exceptionHandlingConfigurer ->
                exceptionHandlingConfigurer.authenticationEntryPoint(new UnauthorizedEntryPoint()))
        .build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }
}

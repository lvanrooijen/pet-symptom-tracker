package com.laila.pet_symptom_tracker.entities.authentication;

import com.laila.pet_symptom_tracker.entities.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BasicAuthenticationProvider implements AuthenticationProvider {
  private final UserService userService;
  private final PasswordEncoder passwordEncoder;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String email = authentication.getName();
    String password = authentication.getCredentials().toString();

    UserDetails user = userService.loadUserByUsername(email);
    if (passwordEncoder.matches(password, user.getPassword())) {
      return new UsernamePasswordAuthenticationToken(email, password, user.getAuthorities());
    } else {
      throw new BadCredentialsException("Authentication failed, bad credentials");
    }
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }
}

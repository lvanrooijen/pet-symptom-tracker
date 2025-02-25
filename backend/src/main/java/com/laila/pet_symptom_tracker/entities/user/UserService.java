package com.laila.pet_symptom_tracker.entities.user;

import com.laila.pet_symptom_tracker.entities.authentication.Role;
import com.laila.pet_symptom_tracker.entities.authentication.dto.LoginRequest;
import com.laila.pet_symptom_tracker.entities.authentication.dto.RegisterRequest;
import com.laila.pet_symptom_tracker.exceptions.authentication.InvalidLoginAttemptException;
import com.laila.pet_symptom_tracker.mainconfig.TerminalColors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsManager {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  /* ~~~~~~~~~~~~~~ Controller methods ~~~~~~~~~~~~~~ */
  public User register(RegisterRequest registerRequest) {
    User user =
        new User.Builder()
            .username(registerRequest.username())
            .email(registerRequest.email())
            .password(passwordEncoder.encode(registerRequest.password()))
            .firstname(registerRequest.firstname())
            .lastname(registerRequest.lastname())
            .role(Role.USER)
            .build();

    log.info(TerminalColors.printInBlue(user.toString()));
    createUser(user);

    return user;
  }

  public User login(LoginRequest loginRequest) {
    User user =
        userRepository
            .findByEmailIgnoreCase(loginRequest.username())
            .orElseThrow(() -> new UsernameNotFoundException("user not found"));

    if (passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
      log.info(TerminalColors.printInBlue("Passwords match"));
      return user;
    } else {
      throw new InvalidLoginAttemptException("Login failed");
    }
  }

  /* ~~~~~~~~~~~~~~ Helper methods ~~~~~~~~~~~~~~ */

  public User findByEmail(String email) {
    return userRepository
        .findByEmailIgnoreCase(email)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }

  /* ~~~~~~~~~~~~~~ Spring Security methods ~~~~~~~~~~~~~~ */

  @Override
  public void createUser(UserDetails user) {
    log.info(
        TerminalColors.printInPink(
            "User: name: " + user.getUsername() + " enabled: " + ((User) user).isEnabled()));
    if (user instanceof User) {
      userRepository.save((User) user);
    } else {
      throw new IllegalArgumentException(
          "Class: UserService, Method: createUser, Problem: object is not instance of User");
    }
  }

  @Override
  public void updateUser(UserDetails user) {}

  @Override
  public void deleteUser(String username) {}

  @Override
  public void changePassword(String oldPassword, String newPassword) {}

  @Override
  public boolean userExists(String email) {
    User user = userRepository.findByEmailIgnoreCase(email).orElse(null);
    return user != null;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return userRepository
        .findByEmailIgnoreCase(email)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }
}

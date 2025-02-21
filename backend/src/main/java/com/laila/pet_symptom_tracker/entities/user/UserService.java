package com.laila.pet_symptom_tracker.entities.user;

import com.laila.pet_symptom_tracker.entities.authentication.dto.RegisterUser;
import com.laila.pet_symptom_tracker.entities.user.dto.GetUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsManager {
  private final UserRepository userRepository;
  private PasswordEncoder passwordEncoder;

  /* ~~~~~~~~~~~~~~ Controller methods ~~~~~~~~~~~~~~ */
  public GetUser register(RegisterUser registerUser) {
    User user =
        new User.Builder()
            .username(registerUser.username())
            .email(registerUser.email())
            .password(passwordEncoder.encode(registerUser.password()))
            .firstname(registerUser.firstname())
            .lastname(registerUser.lastname())
            .build();

    createUser(user);

    return GetUser.from(user);
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

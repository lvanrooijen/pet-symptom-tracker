package com.laila.pet_symptom_tracker.entities.user;

import com.laila.pet_symptom_tracker.entities.user.dto.RegisterDto;
import com.laila.pet_symptom_tracker.exceptions.BadRequestException;
import com.laila.pet_symptom_tracker.exceptions.UsernameNotFoundException;
import com.laila.pet_symptom_tracker.util.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;

  public User register(RegisterDto dto) {
    if (userRepository.findByUsernameIgnoreCase(dto.username()).isPresent())
      throw new IllegalArgumentException("Username already exists.");

    if (userRepository.findByEmailIgnoreCase(dto.email()).isPresent()) {
      throw new BadRequestException("A user with this email has already been registered");
    }

    if (!UserValidator.isValidPasswordPattern(dto.password())) {
      throw new BadRequestException(UserValidator.getPasswordRequirements());
    }

    if (!UserValidator.isValidEmailPattern(dto.email())) {
      throw new BadRequestException("Invalid email");
    }

    return userRepository.save(
        new User(dto.email(), passwordEncoder.encode(dto.password()), dto.username(), Role.USER));
  }

  @Override
  public User loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository
        .findByUsernameIgnoreCase(username)
        .orElseThrow(UsernameNotFoundException::new);
  }
}

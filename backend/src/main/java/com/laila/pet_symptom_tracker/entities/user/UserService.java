package com.laila.pet_symptom_tracker.entities.user;

import com.laila.pet_symptom_tracker.entities.user.dto.GetUser;
import com.laila.pet_symptom_tracker.entities.user.dto.PatchUser;
import com.laila.pet_symptom_tracker.entities.user.dto.RegisterDto;
import com.laila.pet_symptom_tracker.entities.user.enums.Role;
import com.laila.pet_symptom_tracker.entities.user.enums.UserControllerActions;
import com.laila.pet_symptom_tracker.exceptions.BadRequestException;
import com.laila.pet_symptom_tracker.exceptions.ForbiddenException;
import com.laila.pet_symptom_tracker.exceptions.NotFoundException;
import com.laila.pet_symptom_tracker.exceptions.UsernameNotFoundException;
import com.laila.pet_symptom_tracker.util.validator.UserValidator;
import java.util.List;
import java.util.UUID;
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
      throw new BadRequestException("User with this email has already been registered");
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

  public GetUser getById(UUID id, User loggedInUser) {
    User user = userRepository.findById(id).orElseThrow(UsernameNotFoundException::new);

    if (isSameUser(user, loggedInUser) || isModerator(loggedInUser) || isAdmin(loggedInUser)) {
      return GetUser.to(user);
    } else {
      throw new ForbiddenException();
    }
  }

  @Override
  public User loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository
        .findByUsernameIgnoreCase(username)
        .orElseThrow(UsernameNotFoundException::new);
  }

  public List<GetUser> GetAll(User loggedInUser) {
    if (isAdmin(loggedInUser) || isModerator(loggedInUser)) {
      return userRepository.findAll().stream().map(GetUser::to).toList();
    } else {
      throw new ForbiddenException();
    }
  }

  public void deleteById(UUID id, User loggedInUser) {
    User userToDelete = userRepository.findById(id).orElseThrow(NotFoundException::new);

    if (isAdmin(loggedInUser) || isSameUser(userToDelete, loggedInUser)) {
      userRepository.deleteById(id);
    } else {
      throw new ForbiddenException();
    }
  }

  public GetUser banUnbanUser(UUID id, User loggedInUser, UserControllerActions action) {
    User user = userRepository.findById(id).orElseThrow(NotFoundException::new);

    if (isAdmin(loggedInUser) || isModerator(loggedInUser)) {
      user.setEnabled(UserControllerActions.ENABLE == action);
    } else {
      throw new ForbiddenException();
    }

    return GetUser.to(userRepository.save(user));
  }

  public GetUser update(UUID id, User loggedInUser, PatchUser patch) {
    User patchUser = userRepository.findById(id).orElseThrow(NotFoundException::new);

    if (!isSameUser(loggedInUser, patchUser) || !isAdmin(loggedInUser)) {
      throw new ForbiddenException();
    }

    if (patch.firstname() != null) {
      patchUser.setFirstName(patch.firstname());
    }
    if (patch.lastname() != null) {
      patchUser.setLastName(patch.lastname());
    }

    return GetUser.to(userRepository.save(patchUser));
  }

  private Boolean isAdmin(User user) {
    return user.hasRole(Role.ADMIN);
  }

  private Boolean isModerator(User user) {
    return user.hasRole(Role.MODERATOR);
  }

  private Boolean isSameUser(User user1, User user2) {
    return user1.getId() == user2.getId();
  }
}

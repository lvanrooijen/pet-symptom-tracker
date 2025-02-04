package com.laila.pet_symptom_tracker.entities.user;

import com.laila.pet_symptom_tracker.entities.user.dto.UserAuthDto;
import com.laila.pet_symptom_tracker.exceptions.ForbiddenException;
import com.laila.pet_symptom_tracker.exceptions.NotFoundException;
import com.laila.pet_symptom_tracker.mainconfig.Routes;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Routes.USERS)
@RequiredArgsConstructor
@CrossOrigin(origins = "${pet_symptom_tracker.cors}")
public class UserController {
  private final UserRepository userRepository;

  // todo finish deze
  // admin & mods mogen enkele of lijsten van users bekijken
  // admin mag users verwijderen & disabelen
  // admin & mods mogen users enablen en disabelen

  @GetMapping("/{id}")
  private UserAuthDto getById(@PathVariable UUID id, Authentication authentication) {
    // todo dit netjes naar de service verplaatsen
    User authenticatedUser = (User) authentication.getPrincipal();
    if (!(authenticatedUser.getId().equals(id)
        || authenticatedUser.hasRole(Role.ADMIN)
        || authenticatedUser.hasRole(Role.MODERATOR))) throw new ForbiddenException();

    User user = userRepository.findById(id).orElseThrow(NotFoundException::new);

    return UserAuthDto.from(user);
  }
}

package com.laila.pet_symptom_tracker.entities.pettype;

import com.laila.pet_symptom_tracker.entities.pettype.dto.PostPetType;
import com.laila.pet_symptom_tracker.entities.user.User;
import com.laila.pet_symptom_tracker.mainconfig.Routes;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping(Routes.PET_TYPES)
public class PetTypeController {
  private final PetTypeService petTypeService;

  @PostMapping
  public ResponseEntity<PetType> create(
      Authentication authentication, @RequestBody PostPetType postPetType) {
    User loggedInUser = (User) authentication.getPrincipal();
    PetType createdPetType = petTypeService.create(postPetType, loggedInUser);

    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(createdPetType.getId())
            .toUri();

    return ResponseEntity.created(location).body(createdPetType);
  }
}

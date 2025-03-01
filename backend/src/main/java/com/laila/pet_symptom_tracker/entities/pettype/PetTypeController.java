package com.laila.pet_symptom_tracker.entities.pettype;

import com.laila.pet_symptom_tracker.entities.pettype.dto.GetPetType;
import com.laila.pet_symptom_tracker.entities.pettype.dto.PatchPetType;
import com.laila.pet_symptom_tracker.entities.pettype.dto.PostPetType;
import com.laila.pet_symptom_tracker.entities.user.User;
import com.laila.pet_symptom_tracker.mainconfig.Routes;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping(Routes.PET_TYPES)
public class PetTypeController {
  private final PetTypeService petTypeService;

  @PostMapping
  public ResponseEntity<GetPetType> create(
      Authentication authentication, @RequestBody PostPetType postPetType) {
    User loggedInUser = (User) authentication.getPrincipal();
    GetPetType createdPetType = petTypeService.create(postPetType, loggedInUser);

    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(createdPetType.id())
            .toUri();

    return ResponseEntity.created(location).body(createdPetType);
  }

  @GetMapping
  public ResponseEntity<List<GetPetType>> getAll() {
    List<GetPetType> petTypes = petTypeService.getAll();
    return ResponseEntity.ok(petTypes);
  }

  @GetMapping("/{id}")
  public ResponseEntity<GetPetType> getById(@PathVariable Long id) {
    GetPetType petType = petTypeService.getById(id);
    return ResponseEntity.ok(petType);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<GetPetType> update(@PathVariable Long id, @RequestBody PatchPetType patch) {
    GetPetType updatedPetType = petTypeService.patch(id, patch);
    return ResponseEntity.ok(updatedPetType);
  }
}

package com.laila.pet_symptom_tracker.entities.pets;

import com.laila.pet_symptom_tracker.entities.pets.dto.GetPet;
import com.laila.pet_symptom_tracker.entities.pets.dto.PatchPet;
import com.laila.pet_symptom_tracker.entities.pets.dto.PostPet;
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
@RequestMapping(Routes.PETS)
@RequiredArgsConstructor
public class PetController {
  private final PetService petService;

  // create
  @PostMapping
  public ResponseEntity<GetPet> create(@RequestBody PostPet petDto, Authentication authentication) {
    User loggedInUser = (User) authentication.getPrincipal();

    GetPet createdPet = petService.create(loggedInUser, petDto);

    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(createdPet.id())
            .toUri();

    return ResponseEntity.created(location).body(createdPet);
  }

  // get by id
  @GetMapping("/{id}")
  public ResponseEntity<GetPet> getById(@PathVariable Long id, Authentication authentication) {
    User loggedInUser = (User) authentication.getPrincipal();
    return ResponseEntity.ok(petService.getById(loggedInUser, id));
  }

  // get all
  @GetMapping
  public ResponseEntity<List<GetPet>> getAll(Authentication authentication) {
    User loggedInUser = (User) authentication.getPrincipal();
    return ResponseEntity.ok(petService.getAll(loggedInUser));
  }

  // update
  @PatchMapping("/{id}")
  public ResponseEntity<GetPet> update(
      @PathVariable Long id, @RequestBody PatchPet patch, Authentication authentication) {
    User loggedInUser = (User) authentication.getPrincipal();
    return ResponseEntity.ok(petService.update(loggedInUser, id, patch));
  }

  // delete
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id, Authentication authentication) {
    User loggedInUser = (User) authentication.getPrincipal();
    petService.delete(loggedInUser, id);
    return ResponseEntity.ok().build();
  }
}

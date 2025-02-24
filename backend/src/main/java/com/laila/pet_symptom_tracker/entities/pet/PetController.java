package com.laila.pet_symptom_tracker.entities.pet;

import com.laila.pet_symptom_tracker.entities.pet.dto.GetPet;
import com.laila.pet_symptom_tracker.entities.pet.dto.PatchPet;
import com.laila.pet_symptom_tracker.entities.pet.dto.PostPet;
import com.laila.pet_symptom_tracker.entities.user.User;
import com.laila.pet_symptom_tracker.entities.user.UserRepository;
import com.laila.pet_symptom_tracker.exceptions.generic.NotFoundException;
import com.laila.pet_symptom_tracker.mainconfig.Routes;
import com.laila.pet_symptom_tracker.mainconfig.TerminalColors;
import java.net.URI;
import java.util.List;
import java.util.logging.Logger;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(Routes.PETS)
@RequiredArgsConstructor
public class PetController {
  private final Logger log = Logger.getLogger(PetController.class.getName());
  private final PetService petService;
  private final UserRepository userRepository;

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

  @GetMapping("/{id}")
  public ResponseEntity<GetPet> getById(@PathVariable Long id, Authentication authentication) {
    User loggedInUser = (User) authentication.getPrincipal();
    return ResponseEntity.ok(petService.getById(loggedInUser, id));
  }

  @GetMapping
  public ResponseEntity<List<GetPet>> getAll(Authentication authentication) {
    User loggedInUser =
        userRepository
            .findByEmailIgnoreCase(authentication.getName())
            .orElseThrow(NotFoundException::new);
    log.info(
        TerminalColors.setInfoColor(
            "Authentication is: " + authentication)); // TODO was hier gebleven!
    return ResponseEntity.ok(petService.getAll(loggedInUser));
  }

  // TODO ff om te oefenen
  @GetMapping("/test/{test}")
  public ResponseEntity<String> denyIfNotOke(@PathVariable String test) {
    return ResponseEntity.ok(test);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<GetPet> update(
      @PathVariable Long id, @RequestBody PatchPet patch, Authentication authentication) {
    User loggedInUser = (User) authentication.getPrincipal();
    return ResponseEntity.ok(petService.update(loggedInUser, id, patch));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id, Authentication authentication) {
    User loggedInUser = (User) authentication.getPrincipal();
    petService.delete(loggedInUser, id);
    return ResponseEntity.ok().build();
  }
}

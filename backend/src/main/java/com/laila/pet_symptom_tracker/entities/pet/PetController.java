package com.laila.pet_symptom_tracker.entities.pet;

import com.laila.pet_symptom_tracker.entities.pet.dto.GetPet;
import com.laila.pet_symptom_tracker.entities.pet.dto.PatchPet;
import com.laila.pet_symptom_tracker.entities.pet.dto.PostPet;
import com.laila.pet_symptom_tracker.mainconfig.Routes;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(Routes.PETS)
@RequiredArgsConstructor
public class PetController {
  private final PetService petService;

  @PostMapping
  public ResponseEntity<GetPet> create(@RequestBody @Valid PostPet petDto) {
    GetPet createdPet = petService.create(petDto);

    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(createdPet.id())
            .toUri();

    return ResponseEntity.created(location).body(createdPet);
  }

  @GetMapping("/{id}")
  public ResponseEntity<GetPet> getById(@PathVariable Long id) {
    return ResponseEntity.ok(petService.getById(id));
  }

  @GetMapping
  public ResponseEntity<List<GetPet>> getAll() {
    return ResponseEntity.ok(petService.getAll());
  }

  @PatchMapping("/{id}")
  public ResponseEntity<GetPet> update(@PathVariable Long id, @RequestBody PatchPet patch) {
    return ResponseEntity.ok(petService.update(id, patch));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    petService.delete(id);
    return ResponseEntity.ok().build();
  }
}

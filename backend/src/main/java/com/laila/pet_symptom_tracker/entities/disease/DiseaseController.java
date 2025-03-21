package com.laila.pet_symptom_tracker.entities.disease;

import com.laila.pet_symptom_tracker.entities.disease.dto.GetDisease;
import com.laila.pet_symptom_tracker.entities.disease.dto.PatchDisease;
import com.laila.pet_symptom_tracker.entities.disease.dto.PostDisease;
import com.laila.pet_symptom_tracker.entities.user.User;
import com.laila.pet_symptom_tracker.mainconfig.Routes;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(Routes.DISEASE)
@RequiredArgsConstructor
public class DiseaseController {
  private final DiseaseService diseaseService;

  @PostMapping
  public ResponseEntity<GetDisease> create(@RequestBody @Valid PostDisease body) {

    GetDisease disease = diseaseService.create(body);
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(disease.id())
            .toUri();
    return ResponseEntity.ok().body(disease);
  }

  @GetMapping
  public ResponseEntity<List<GetDisease>> getAll() {
    List<GetDisease> diseases = diseaseService.getAll();

    if (diseases.isEmpty()) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.ok(diseases);
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<GetDisease> getById(@PathVariable Long id) {
    return ResponseEntity.ok(diseaseService.getById(id));
  }

  @PatchMapping("/{id}")
  public ResponseEntity<GetDisease> update(@PathVariable Long id, @RequestBody PatchDisease patch) {
    GetDisease updatedDisease = diseaseService.update(id, patch);
    return ResponseEntity.ok(updatedDisease);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    diseaseService.delete(id);
    return ResponseEntity.ok().build();
  }
}

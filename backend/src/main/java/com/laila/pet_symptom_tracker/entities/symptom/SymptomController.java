package com.laila.pet_symptom_tracker.entities.symptom;

import com.laila.pet_symptom_tracker.entities.symptom.dto.GetSymptom;
import com.laila.pet_symptom_tracker.entities.symptom.dto.PatchSymptom;
import com.laila.pet_symptom_tracker.entities.symptom.dto.PostSymptom;
import com.laila.pet_symptom_tracker.entities.user.User;
import com.laila.pet_symptom_tracker.mainconfig.Routes;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Controller
@RequiredArgsConstructor
@RequestMapping(Routes.SYMPTOM)
public class SymptomController {
  private final SymptomService symptomService;

  @PostMapping
  public ResponseEntity<GetSymptom> create(
      @RequestBody PostSymptom postSymptom, Authentication authentication) {
    User loggedInUser = (User) authentication.getPrincipal();
    GetSymptom symptom = symptomService.create(postSymptom, loggedInUser);
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(symptom.id())
            .toUri();
    return ResponseEntity.created(location).body(symptom);
  }

  @GetMapping
  public ResponseEntity<List<GetSymptom>> getAll(Authentication authentication) {
    User loggedInUser = (User) authentication.getPrincipal();
    List<GetSymptom> symptoms = symptomService.getAll(loggedInUser);

    return ResponseEntity.ok(symptoms);
  }

  @GetMapping("/{id}")
  public ResponseEntity<GetSymptom> getById(@PathVariable Long id, Authentication authentication) {
    User loggedInUser = (User) authentication.getPrincipal();
    return ResponseEntity.ok(symptomService.getById(id));
  }

  @PatchMapping("/{id}")
  public ResponseEntity<GetSymptom> update(
      @PathVariable Long id, @RequestBody PatchSymptom patch, Authentication authentication) {
    User loggedInUser = (User) authentication.getPrincipal();
    GetSymptom updatedSymptom = symptomService.update(id, patch, loggedInUser);
    return ResponseEntity.ok(updatedSymptom);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id, Authentication authentication) {
    symptomService.delete(id);
    return ResponseEntity.ok().build();
  }
}

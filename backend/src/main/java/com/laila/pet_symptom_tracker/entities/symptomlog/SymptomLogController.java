package com.laila.pet_symptom_tracker.entities.symptomlog;

import com.laila.pet_symptom_tracker.entities.symptomlog.dto.GetSymptomLog;
import com.laila.pet_symptom_tracker.entities.symptomlog.dto.PatchSymptomLog;
import com.laila.pet_symptom_tracker.entities.symptomlog.dto.PostSymptomLog;
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
@RequestMapping(Routes.SYMPTOM_LOG)
@RequiredArgsConstructor
public class SymptomLogController {
  private final SymptomLogService symptomLogService;

  @PostMapping
  public ResponseEntity<GetSymptomLog> create(
      @RequestBody @Valid PostSymptomLog symptom, Authentication authentication) {
    User loggedInUser = (User) authentication.getPrincipal();
    GetSymptomLog createdSymptom = symptomLogService.create(loggedInUser, symptom);
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(createdSymptom.id())
            .toUri();

    return ResponseEntity.created(location).body(createdSymptom);
  }

  @GetMapping
  public ResponseEntity<List<GetSymptomLog>> getAll(Authentication authentication) {
    User loggedInUser = (User) authentication.getPrincipal();
    List<GetSymptomLog> symptomLogs = symptomLogService.findAll(loggedInUser);

    if (symptomLogs.isEmpty()) {
      return ResponseEntity.noContent().build();
    }

    return ResponseEntity.ok(symptomLogs);
  }

  @GetMapping("/{id}")
  public ResponseEntity<GetSymptomLog> getById(
      @PathVariable Long id, Authentication authentication) {
    User loggedInUser = (User) authentication.getPrincipal();
    GetSymptomLog symptomLog = symptomLogService.getById(loggedInUser, id);
    return ResponseEntity.ok(symptomLog);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<GetSymptomLog> update(
      @PathVariable Long id, Authentication authentication, @RequestBody PatchSymptomLog patch) {
    User loggedInUser = (User) authentication.getPrincipal();
    GetSymptomLog updatedSymptomLog = symptomLogService.update(id, loggedInUser, patch);
    return ResponseEntity.ok(updatedSymptomLog);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id, Authentication authentication) {
    User loggedInUser = (User) authentication.getPrincipal();
    symptomLogService.delete(id, loggedInUser);
    return ResponseEntity.ok().build();
  }
}

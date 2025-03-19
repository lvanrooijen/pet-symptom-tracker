package com.laila.pet_symptom_tracker.entities.diseaselog;

import com.laila.pet_symptom_tracker.entities.diseaselog.dto.GetDiseaseLog;
import com.laila.pet_symptom_tracker.entities.diseaselog.dto.PatchDiseaseLog;
import com.laila.pet_symptom_tracker.entities.diseaselog.dto.PostDiseaseLog;
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
@RequestMapping(Routes.DISEASE_LOG)
@RequiredArgsConstructor
public class DiseaseLogController {
  private final DiseaseLogService diseaseLogService;

  @PostMapping
  public ResponseEntity<GetDiseaseLog> create(
      @RequestBody @Valid PostDiseaseLog diseaseLog, Authentication authentication) {
    User loggedInUser = (User) authentication.getPrincipal();
    GetDiseaseLog createdDiseaseLog = diseaseLogService.create(diseaseLog, loggedInUser);

    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(createdDiseaseLog.id())
            .toUri();
    return ResponseEntity.created(location).body(createdDiseaseLog);
  }

  @GetMapping
  public ResponseEntity<List<GetDiseaseLog>> getAll(Authentication authentication) {
    User loggedInUser = (User) authentication.getPrincipal();
    List<GetDiseaseLog> logs = diseaseLogService.getAll(loggedInUser);

    if (logs.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(logs);
  }

  @GetMapping("/{id}")
  public ResponseEntity<GetDiseaseLog> getById(
      @PathVariable Long id, Authentication authentication) {
    User loggedInUser = (User) authentication.getPrincipal();
    GetDiseaseLog diseaseLog = diseaseLogService.getById(id, loggedInUser);

    return ResponseEntity.ok(diseaseLog);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<GetDiseaseLog> update(
      @PathVariable Long id, @RequestBody PatchDiseaseLog patch, Authentication authentication) {
    User loggedInUser = (User) authentication.getPrincipal();
    GetDiseaseLog updateDiseaseLog = diseaseLogService.update(id, patch, loggedInUser);
    return ResponseEntity.ok(updateDiseaseLog);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id, Authentication authentication) {
    User loggedInUser = (User) authentication.getPrincipal();
    diseaseLogService.delete(id, loggedInUser);
    return ResponseEntity.ok().build();
  }
}

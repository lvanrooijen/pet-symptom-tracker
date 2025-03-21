package com.laila.pet_symptom_tracker.entities.symptomlog;

import com.laila.pet_symptom_tracker.entities.symptomlog.dto.GetSymptomLog;
import com.laila.pet_symptom_tracker.entities.symptomlog.dto.PatchSymptomLog;
import com.laila.pet_symptom_tracker.entities.symptomlog.dto.PostSymptomLog;
import com.laila.pet_symptom_tracker.mainconfig.Routes;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(Routes.SYMPTOM_LOG)
@RequiredArgsConstructor
public class SymptomLogController {
  private final SymptomLogService symptomLogService;

  @PostMapping
  public ResponseEntity<GetSymptomLog> create(@RequestBody @Valid PostSymptomLog symptom) {
    GetSymptomLog createdSymptom = symptomLogService.create(symptom);
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(createdSymptom.id())
            .toUri();

    return ResponseEntity.created(location).body(createdSymptom);
  }

  @GetMapping
  public ResponseEntity<List<GetSymptomLog>> getAll() {
    List<GetSymptomLog> symptomLogs = symptomLogService.findAll();

    if (symptomLogs.isEmpty()) {
      return ResponseEntity.noContent().build();
    }

    return ResponseEntity.ok(symptomLogs);
  }

  @GetMapping("/{id}")
  public ResponseEntity<GetSymptomLog> getById(@PathVariable Long id) {
    GetSymptomLog symptomLog = symptomLogService.getById(id);
    return ResponseEntity.ok(symptomLog);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<GetSymptomLog> update(
      @PathVariable Long id, @RequestBody PatchSymptomLog patch) {
    GetSymptomLog updatedSymptomLog = symptomLogService.update(id, patch);
    return ResponseEntity.ok(updatedSymptomLog);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    symptomLogService.delete(id);
    return ResponseEntity.ok().build();
  }
}

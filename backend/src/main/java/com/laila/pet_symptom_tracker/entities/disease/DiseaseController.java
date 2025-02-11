package com.laila.pet_symptom_tracker.entities.disease;

import com.laila.pet_symptom_tracker.mainconfig.Routes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Routes.DISEASE)
@CrossOrigin(origins = "{pet_symptom_tracker.cors}")
public class DiseaseController {
  @PostMapping
  public ResponseEntity<GetDisease> create(@RequestBody PostDisease body) {}
}

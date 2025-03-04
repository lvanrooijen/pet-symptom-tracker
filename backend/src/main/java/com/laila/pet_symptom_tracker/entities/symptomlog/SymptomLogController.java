package com.laila.pet_symptom_tracker.entities.symptomlog;

import com.laila.pet_symptom_tracker.mainconfig.Routes;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Routes.SYMPTOM_LOG)
@RequiredArgsConstructor
public class SymptomLogController {
  private final SymptomLogService symptomLogService;
}

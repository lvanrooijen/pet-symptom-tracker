package com.laila.pet_symptom_tracker.entities.symptomlog.dto;

import com.laila.pet_symptom_tracker.entities.symptom.dto.GetSymptomCompact;
import com.laila.pet_symptom_tracker.entities.symptomlog.SymptomLog;
import java.time.LocalDate;

public record GetSymptomLog(
    Long id, GetSymptomCompact symptom, String details, LocalDate reportDate) {
  public static GetSymptomLog from(SymptomLog entity) {
    return new GetSymptomLog(
        entity.getId(),
        GetSymptomCompact.from(entity.getSymptom()),
        entity.getDetails(),
        entity.getReportDate());
  }
}

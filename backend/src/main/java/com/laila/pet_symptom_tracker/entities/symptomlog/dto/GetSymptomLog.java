package com.laila.pet_symptom_tracker.entities.symptomlog.dto;

import com.laila.pet_symptom_tracker.entities.symptom.dto.GetSymptom;
import com.laila.pet_symptom_tracker.entities.symptomlog.SymptomLog;
import java.time.LocalDate;
import java.time.LocalTime;

public record GetSymptomLog(
    Long id, GetSymptom symptom, String details, LocalDate reportDate, LocalTime reportTime) {
  public static GetSymptomLog from(SymptomLog entity) {
    return new GetSymptomLog(
        entity.getId(),
        GetSymptom.from(entity.getSymptom()),
        entity.getDetails(),
        entity.getReportDate(),
        entity.getReportTime());
  }
}

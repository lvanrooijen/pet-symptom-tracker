package com.laila.pet_symptom_tracker.entities.symptom.dto;

import com.laila.pet_symptom_tracker.entities.symptom.Symptom;

public record GetSymptom(Long id, String name, String description, Boolean isVerified) {
  public static GetSymptom from(Symptom entity) {
    return new GetSymptom(
        entity.getId(), entity.getName(), entity.getDescription(), entity.getIsVerified());
  }
}

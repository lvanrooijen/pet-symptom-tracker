package com.laila.pet_symptom_tracker.entities.symptom.dto;

import com.laila.pet_symptom_tracker.entities.symptom.Symptom;

public record GetSymptomCompact(Long id, String name, String description) {
  public static GetSymptomCompact from(Symptom entity) {
    return new GetSymptomCompact(entity.getId(), entity.getName(), entity.getDescription());
  }
}

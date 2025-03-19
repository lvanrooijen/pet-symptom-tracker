package com.laila.pet_symptom_tracker.entities.disease.dto;

import com.laila.pet_symptom_tracker.entities.disease.Disease;

public record GetDiseaseCompact(Long id, String name, String description) {
  public static GetDiseaseCompact from(Disease entity) {
    return new GetDiseaseCompact(entity.getId(), entity.getName(), entity.getDescription());
  }
}

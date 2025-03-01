package com.laila.pet_symptom_tracker.entities.disease.dto;

import com.laila.pet_symptom_tracker.entities.disease.Disease;

public record GetDisease(Long id, String name, String description, String createdBy) {
  public static GetDisease from(Disease entity) {
    return new GetDisease(
        entity.getId(),
        entity.getName(),
        entity.getDescription(),
        entity.getCreatedBy().getUsername());
  }
}

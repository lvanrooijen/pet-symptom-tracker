package com.laila.pet_symptom_tracker.entities.pettype.dto;

import com.laila.pet_symptom_tracker.entities.pettype.PetType;

public record PetTypeResponse(Long id, String name, String createdBy, Boolean deleted) {
  public static PetTypeResponse from(PetType entity) {
    return new PetTypeResponse(
        entity.getId(), entity.getName(), entity.getCreatedBy().getUsername(), entity.isDeleted());
  }
}

package com.laila.pet_symptom_tracker.entities.pettype.dto;

import com.laila.pet_symptom_tracker.entities.pettype.PetType;

public record GetPetType(Long id, String name) {
  public static GetPetType from(PetType entity) {
    return new GetPetType(entity.getId(), entity.getName());
  }
}

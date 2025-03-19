package com.laila.pet_symptom_tracker.entities.pettype.dto;

import com.laila.pet_symptom_tracker.entities.pettype.PetType;

public record GetPetTypeCompact(Long id, String name) {
  public static GetPetTypeCompact from(PetType entity) {
    return new GetPetTypeCompact(entity.getId(), entity.getName());
  }
}

package com.laila.pet_symptom_tracker.entities.breed.dto;

import com.laila.pet_symptom_tracker.entities.breed.Breed;
import com.laila.pet_symptom_tracker.entities.pettype.dto.GetPetTypeCompact;

public record GetBreed(Long id, String name, GetPetTypeCompact petType, String createdBy) {
  public static GetBreed from(Breed entity) {
    return new GetBreed(
        entity.getId(), entity.getName(), GetPetTypeCompact.from(entity.getPetType()), entity.getCreatedBy().getUsername());
  }
}

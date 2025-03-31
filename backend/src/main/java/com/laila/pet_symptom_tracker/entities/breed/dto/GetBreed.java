package com.laila.pet_symptom_tracker.entities.breed.dto;

import com.laila.pet_symptom_tracker.entities.breed.Breed;
import com.laila.pet_symptom_tracker.entities.pettype.dto.GetPetTypeCompact;
import java.util.UUID;

public record GetBreed(Long id, String name, GetPetTypeCompact petType, UUID creatorId) {
  public static GetBreed from(Breed entity) {
    return new GetBreed(
        entity.getId(), entity.getName(), GetPetTypeCompact.from(entity.getPetType()), entity.getCreatedBy().getId());
  }
}

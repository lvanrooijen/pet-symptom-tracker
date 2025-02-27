package com.laila.pet_symptom_tracker.entities.breed.dto;

import com.laila.pet_symptom_tracker.entities.breed.Breed;

public record GetBreed(Long id, String name, Long petTypeId) {
  public static GetBreed from(Breed entity) {
    return new GetBreed(entity.getId(), entity.getName(), entity.getPetType().getId());
  }
}

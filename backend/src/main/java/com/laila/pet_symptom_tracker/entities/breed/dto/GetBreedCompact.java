package com.laila.pet_symptom_tracker.entities.breed.dto;

import com.laila.pet_symptom_tracker.entities.breed.Breed;

public record GetBreedCompact(Long id, String name) {
  public static GetBreedCompact from(Breed entity) {
    return new GetBreedCompact(entity.getId(), entity.getName());
  }
}

package com.laila.pet_symptom_tracker.entities.pet.dto;

import com.laila.pet_symptom_tracker.entities.breed.dto.GetBreed;
import com.laila.pet_symptom_tracker.entities.pet.Pet;

import java.time.LocalDate;

public record GetPetCompact(Long id, String name) {
  public static GetPetCompact from(Pet entity) {
    return new GetPetCompact(entity.getId(), entity.getName());
  }
}

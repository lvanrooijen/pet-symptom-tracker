package com.laila.pet_symptom_tracker.entities.pet.dto;

import com.laila.pet_symptom_tracker.entities.pet.Pet;
import java.time.LocalDate;

public record PostPet(String name, LocalDate dateOfBirth, Boolean isAlive) {
  public static Pet to(PostPet dto) {
    return new Pet(dto.name, dto.dateOfBirth, true);
  }
}

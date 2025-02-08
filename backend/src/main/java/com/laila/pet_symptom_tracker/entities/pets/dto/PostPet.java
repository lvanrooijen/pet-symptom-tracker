package com.laila.pet_symptom_tracker.entities.pets.dto;

import com.laila.pet_symptom_tracker.entities.pets.Pet;
import java.time.LocalDate;

public record PostPet(String name, LocalDate dateOfBirth, Boolean isAlive) {
  public static Pet to(PostPet dto) {
    return new Pet(dto.name, dto.dateOfBirth, true);
  }
}

package com.laila.pet_symptom_tracker.entities.pet.dto;

import com.laila.pet_symptom_tracker.entities.pet.Pet;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record PostPet(@NotBlank String name, @NotNull LocalDate dateOfBirth, @NotNull Long petTypeId) {
  public static Pet to(PostPet dto) {
    return new Pet(dto.name, dto.dateOfBirth, true);
  }
}

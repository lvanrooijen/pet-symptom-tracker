package com.laila.pet_symptom_tracker.entities.pettype.dto;

import com.laila.pet_symptom_tracker.entities.pettype.PetType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PostPetType(
    @NotBlank @Size(min = 3, max = 30, message = "Name must be between 3 and 30 characters")
        String name) {
  public static PetType to(PostPetType dto) {
    return new PetType(dto.name);
  }
}

package com.laila.pet_symptom_tracker.entities.pet.dto;

import com.laila.pet_symptom_tracker.entities.breed.dto.GetBreedCompact;
import com.laila.pet_symptom_tracker.entities.pet.Pet;
import java.time.LocalDate;

public record GetPet(
    Long id, String name, LocalDate dateOfBirth, Boolean isAlive, GetBreedCompact breed) {
  public static GetPet from(Pet pet) {
    return new GetPet(
        pet.getId(), pet.getName(), pet.getDateOfBirth(), pet.getIsAlive(), GetBreedCompact.from(pet.getBreed()));
  }
}

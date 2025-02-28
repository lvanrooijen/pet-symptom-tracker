package com.laila.pet_symptom_tracker.entities.pet.dto;

import com.laila.pet_symptom_tracker.entities.breed.Breed;
import com.laila.pet_symptom_tracker.entities.pet.Pet;
import java.time.LocalDate;

public record GetPet(
    Long id, String name, LocalDate dateOfBirth, Boolean isAlive, Breed breed) {
  public static GetPet from(Pet pet) {
    return new GetPet(
        pet.getId(), pet.getName(), pet.getDateOfBirth(), pet.getIsAlive(), pet.getBreed());
  }
}

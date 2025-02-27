package com.laila.pet_symptom_tracker.entities.pet.dto;

import com.laila.pet_symptom_tracker.entities.pet.Pet;
import com.laila.pet_symptom_tracker.entities.pettype.PetType;
import java.time.LocalDate;

public record GetPet(
    Long id, String name, LocalDate dateOfBirth, Boolean isAlive, PetType petType) {
  public static GetPet from(Pet pet) {
    return new GetPet(
        pet.getId(), pet.getName(), pet.getDateOfBirth(), pet.getIsAlive(), pet.getPetType());
  }
}

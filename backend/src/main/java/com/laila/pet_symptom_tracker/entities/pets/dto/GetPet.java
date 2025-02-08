package com.laila.pet_symptom_tracker.entities.pets.dto;

import com.laila.pet_symptom_tracker.entities.pets.Pet;
import java.time.LocalDate;

public record GetPet(Long id, String name, LocalDate dateOfBirth, Boolean isAlive) {
    public static GetPet from(Pet pet){
        return new GetPet(pet.getId(), pet.getName(),pet.getDateOfBirth(),pet.getIsAlive());
    }
}

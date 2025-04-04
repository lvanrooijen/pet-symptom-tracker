package com.laila.pet_symptom_tracker.testdata;

import com.laila.pet_symptom_tracker.entities.pettype.PetType;
import com.laila.pet_symptom_tracker.entities.user.User;

public class PetTypeTestData extends TestData {
  // Pet type
  public PetType getPetType(User creator) {
    return new PetType(VALID_ID, DEFAULT_PET_TYPE.getName(), false, creator);
  }

  public PetType getPetType() {
    return DEFAULT_PET_TYPE;
  }
}

package com.laila.pet_symptom_tracker.testdata;

import com.laila.pet_symptom_tracker.entities.breed.Breed;
import com.laila.pet_symptom_tracker.entities.breed.dto.BreedResponse;
import com.laila.pet_symptom_tracker.entities.breed.dto.PatchBreed;
import com.laila.pet_symptom_tracker.entities.breed.dto.PostBreed;
import com.laila.pet_symptom_tracker.entities.pettype.dto.PetTypeCompactResponse;
import java.util.List;

public class BreedTestData extends TestData {

  public Breed getBreed() {
    return DEFAULT_BREED;
  }

  public PostBreed getPostBreed() {
    return new PostBreed(DEFAULT_BREED.getName(), VALID_ID);
  }

  public PatchBreed getPatchBreed() {
    return new PatchBreed("Test", VALID_ID);
  }

  public PatchBreed getPatchBreed(Long id) {
    return new PatchBreed("Test", id);
  }

  public BreedResponse getBreedResponse() {
    PetTypeCompactResponse petTypeCompact =
        new PetTypeCompactResponse(VALID_ID, DEFAULT_BREED.getName());
    return new BreedResponse(VALID_ID, DEFAULT_BREED.getName(), petTypeCompact, REGULAR_USER_ID);
  }

  public List<BreedResponse> getBreedResponseList() {
    PetTypeCompactResponse petTypeCompact =
        new PetTypeCompactResponse(VALID_ID, DEFAULT_PET_TYPE.getName());
    return List.of(
        new BreedResponse(1L, "Siamese", petTypeCompact, REGULAR_USER_ID),
        new BreedResponse(2L, "Short hair", petTypeCompact, REGULAR_USER_ID),
        new BreedResponse(3L, "Sphinx", petTypeCompact, REGULAR_USER_ID));
  }
}

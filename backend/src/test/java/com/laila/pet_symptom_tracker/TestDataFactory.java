package com.laila.pet_symptom_tracker;

import com.laila.pet_symptom_tracker.entities.authentication.Role;
import com.laila.pet_symptom_tracker.entities.blacklistword.BlackListWord;
import com.laila.pet_symptom_tracker.entities.blacklistword.dto.PatchBlackListWord;
import com.laila.pet_symptom_tracker.entities.blacklistword.dto.PostBlackListWord;
import com.laila.pet_symptom_tracker.entities.breed.Breed;
import com.laila.pet_symptom_tracker.entities.breed.dto.BreedResponse;
import com.laila.pet_symptom_tracker.entities.breed.dto.PatchBreed;
import com.laila.pet_symptom_tracker.entities.breed.dto.PostBreed;
import com.laila.pet_symptom_tracker.entities.disease.Disease;
import com.laila.pet_symptom_tracker.entities.disease.dto.DiseaseCompactResponse;
import com.laila.pet_symptom_tracker.entities.disease.dto.DiseaseResponse;
import com.laila.pet_symptom_tracker.entities.disease.dto.PatchDisease;
import com.laila.pet_symptom_tracker.entities.disease.dto.PostDisease;
import com.laila.pet_symptom_tracker.entities.diseaselog.DiseaseLog;
import com.laila.pet_symptom_tracker.entities.diseaselog.dto.DiseaseLogResponse;
import com.laila.pet_symptom_tracker.entities.diseaselog.dto.PatchDiseaseLog;
import com.laila.pet_symptom_tracker.entities.diseaselog.dto.PostDiseaseLog;
import com.laila.pet_symptom_tracker.entities.pet.Pet;
import com.laila.pet_symptom_tracker.entities.pet.dto.PetCompactResponse;
import com.laila.pet_symptom_tracker.entities.pettype.PetType;
import com.laila.pet_symptom_tracker.entities.pettype.dto.PetTypeCompactResponse;
import com.laila.pet_symptom_tracker.entities.user.User;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class TestDataFactory {
  // id's
  public static final UUID USER_ID = UUID.fromString("00000000-0000-0000-0000-000000000001");
  public static final UUID MODERATOR_ID = UUID.fromString("00000000-0000-0000-0000-000000000002");
  public static final UUID ADMIN_ID = UUID.fromString("00000000-0000-0000-0000-000000000003");
  public static final UUID DEFAULT_USER_ID =
      UUID.fromString("00000000-0000-0000-0000-000000000004");
  public static final Long VALID_ID = 1L;
  public static final Long INVALID_ID = 999L;

  // date's

  public static final LocalDate CREATION_DATE = LocalDate.of(2021, 1, 1);
  public static final LocalDate HEAL_DATE = LocalDate.of(2025, 1, 1);

  // users
  private static final User DEFAULT_USER =
      User.builder()
          .id(DEFAULT_USER_ID)
          .email("default@email.com")
          .password("Password123!")
          .username("default")
          .role(Role.USER)
          .build();

  // blacklist word
  public static PostBlackListWord getPostBlacklistWord() {
    return new PostBlackListWord("fuck");
  }

  public static PatchBlackListWord getPatchBlackListWord() {
    return new PatchBlackListWord("fudge");
  }

  public static BlackListWord getBlackListWord() {
    return new BlackListWord("fuck");
  }

  // user
  public static User getRegularUser() {
    return User.builder()
        .id(USER_ID)
        .email("default@email.com")
        .password("Password123!")
        .username("user")
        .role(Role.USER)
        .build();
  }

  public static User getModerator() {
    return User.builder()
        .id(USER_ID)
        .email("default@email.com")
        .password("Password123!")
        .username("moderator")
        .role(Role.MODERATOR)
        .build();
  }

  public static User getAdmin() {
    return User.builder()
        .id(USER_ID)
        .email("default@email.com")
        .password("Password123!")
        .username("admin")
        .role(Role.ADMIN)
        .build();
  }

  // Breed
  public static Breed getBreed(User creator) {
    PetType petType = new PetType(VALID_ID, "Cat", false, creator);
    return Breed.builder().name("Sphinx").petType(petType).createdBy(creator).build();
  }

  public static Breed getBreed() {
    PetType petType = new PetType(VALID_ID, "Cat", false, DEFAULT_USER);
    return Breed.builder().name("Sphinx").petType(petType).createdBy(DEFAULT_USER).build();
  }

  public static PostBreed getPostBreed() {
    return new PostBreed("Siamese", 1L);
  }

  public static PatchBreed getPatchBreed() {
    return new PatchBreed("Test", VALID_ID);
  }

  public static PatchBreed getPatchBreed(Long id) {
    return new PatchBreed("Test", id);
  }

  public static BreedResponse getBreedResponse() {
    PetTypeCompactResponse petTypeCompact = new PetTypeCompactResponse(VALID_ID, "Cat");
    return new BreedResponse(1L, "Siamese", petTypeCompact, USER_ID);
  }

  public static List<BreedResponse> getBreedResponseList() {
    PetTypeCompactResponse petTypeCompact = new PetTypeCompactResponse(VALID_ID, "Cat");
    return List.of(
        new BreedResponse(1L, "Siamese", petTypeCompact, USER_ID),
        new BreedResponse(2L, "Short hair", petTypeCompact, USER_ID),
        new BreedResponse(3L, "Sphinx", petTypeCompact, USER_ID));
  }

  // Pet
  public static Pet getPet() {
    return Pet.builder()
        .name("Garfield")
        .breed(getBreed())
        .id(VALID_ID)
        .owner(DEFAULT_USER)
        .isAlive(true)
        .dateOfBirth(CREATION_DATE)
        .build();
  }

  public static Pet getPet(User owner) {
    return Pet.builder()
        .name("Garfield")
        .breed(getBreed())
        .id(VALID_ID)
        .owner(owner)
        .isAlive(true)
        .dateOfBirth(CREATION_DATE)
        .build();
  }

  public static PetCompactResponse getPetCompactResponse() {
    return new PetCompactResponse(VALID_ID, "Garfield");
  }

  // Pet type
  public static PetType getPetType(User creator) {
    return new PetType(VALID_ID, "Siamese", false, creator);
  }

  public static PetType getPetType() {
    return new PetType(VALID_ID, "Siamese", false, DEFAULT_USER);
  }

  // Disease
  public static Disease getDisease(User creator) {
    return new Disease(VALID_ID, "Rabies", "description on rabies", false, creator);
  }

  public static Disease getDisease() {
    return new Disease(VALID_ID, "Rabies", "description on rabies", false, DEFAULT_USER);
  }

  public static Disease getDeletedDisease(User creator) {
    return new Disease(VALID_ID, "Rabies", "description on rabies", true, creator);
  }

  public static Disease getDeletedDisease() {
    return new Disease(VALID_ID, "Rabies", "description on rabies", true, DEFAULT_USER);
  }

  public static PostDisease getPostDisease() {
    return new PostDisease("Rabies", "description on rabies");
  }

  public static PatchDisease getPatchDisease() {
    return new PatchDisease("Test", "new description");
  }

  public static DiseaseResponse getDiseaseResponse() {
    return new DiseaseResponse(VALID_ID, "Rabies", "description on rabies", USER_ID);
  }

  public static List<Disease> getAdminDiseaseList() {
    return List.of(
        new Disease(1L, "A", "description on A", false, getAdmin()),
        new Disease(2L, "B", "description on B", false, getAdmin()));
  }

  public static List<Disease> getDiseaseList() {
    return List.of(
        new Disease(1L, "A", "description on A", false, getAdmin()),
        new Disease(2L, "B", "description on B", false, getAdmin()),
        new Disease(3L, "C", "description on C", true, getAdmin()),
        new Disease(4L, "D", "description on D", true, getAdmin()));
  }

  public static DiseaseCompactResponse getDiseaseCompactResponse() {
    return new DiseaseCompactResponse(VALID_ID, "Rabies", "description on rabies");
  }

  // Disease log
  public static DiseaseLog getDiseaseLog() {
    return DiseaseLog.builder().pet(getPet(DEFAULT_USER)).disease(getDisease()).build();
  }

  public static DiseaseLog getDiseaseLog(User owner) {
    return DiseaseLog.builder().pet(getPet(owner)).disease(getDisease()).build();
  }

  public static DiseaseLogResponse getDiseaseLogResponse() {
    return new DiseaseLogResponse(
        VALID_ID, getDiseaseCompactResponse(), getPetCompactResponse(), CREATION_DATE, false, null);
  }

  public static PostDiseaseLog getPostDiseaseLog() {
    return new PostDiseaseLog(VALID_ID, VALID_ID, CREATION_DATE);
  }

  public static PatchDiseaseLog getPatchDiseaseLog() {
    return new PatchDiseaseLog(VALID_ID, CREATION_DATE, true, HEAL_DATE);
  }

  public static List<DiseaseLog> diseaseLogList() {
    return List.of(
        DiseaseLog.builder()
            .pet(getPet())
            .disease(getDisease())
            .isHealed(true)
            .discoveryDate(CREATION_DATE)
            .healedOnDate(HEAL_DATE)
            .build(),
        DiseaseLog.builder()
            .pet(getPet())
            .disease(getDisease())
            .isHealed(true)
            .discoveryDate(CREATION_DATE)
            .healedOnDate(HEAL_DATE)
            .build(),
        DiseaseLog.builder()
            .pet(getPet())
            .disease(getDisease())
            .isHealed(true)
            .discoveryDate(CREATION_DATE)
            .healedOnDate(HEAL_DATE)
            .build());
  }

  public static List<DiseaseLog> diseaseLogListWithOneOwner(User owner) {
    return List.of(
        DiseaseLog.builder()
            .pet(getPet(owner))
            .disease(getDisease())
            .isHealed(true)
            .discoveryDate(CREATION_DATE)
            .healedOnDate(HEAL_DATE)
            .build(),
        DiseaseLog.builder()
            .pet(getPet(DEFAULT_USER))
            .disease(getDisease())
            .isHealed(false)
            .discoveryDate(CREATION_DATE)
            .build(),
        DiseaseLog.builder()
            .pet(getPet(DEFAULT_USER))
            .disease(getDisease())
            .isHealed(false)
            .discoveryDate(CREATION_DATE)
            .build());
  }
}

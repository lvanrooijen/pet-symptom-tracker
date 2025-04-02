package com.laila.pet_symptom_tracker.entities.breed;

import static com.laila.pet_symptom_tracker.TestDataFactory.INVALID_ID;
import static com.laila.pet_symptom_tracker.TestDataFactory.VALID_ID;
import static com.laila.pet_symptom_tracker.exceptions.ExceptionMessages.NON_EXISTENT_PET_TYPE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.laila.pet_symptom_tracker.TestDataFactory;
import com.laila.pet_symptom_tracker.entities.authentication.AuthenticationService;
import com.laila.pet_symptom_tracker.entities.breed.dto.BreedResponse;
import com.laila.pet_symptom_tracker.entities.breed.dto.PatchBreed;
import com.laila.pet_symptom_tracker.entities.breed.dto.PostBreed;
import com.laila.pet_symptom_tracker.entities.pettype.PetType;
import com.laila.pet_symptom_tracker.entities.pettype.PetTypeRepository;
import com.laila.pet_symptom_tracker.entities.user.User;
import com.laila.pet_symptom_tracker.exceptions.generic.BadRequestException;
import com.laila.pet_symptom_tracker.exceptions.generic.NotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
class BreedServiceTest {
  @Mock private BreedRepository breedRepository;
  @Mock private PetTypeRepository petTypeRepository;
  @Mock private AuthenticationService authenticationService;

  @InjectMocks BreedService breedService;

  @Test
  public void create_breed_should_not_return_null() {
    PostBreed postBreed = TestDataFactory.getPostBreed();
    User admin = TestDataFactory.getAdmin();
    PetType petType = TestDataFactory.getPetType(admin);

    when(petTypeRepository.findById(postBreed.petTypeId())).thenReturn(Optional.of(petType));
    when(authenticationService.getAuthenticatedUser()).thenReturn(admin);
    when(breedRepository.save(any(Breed.class))).thenReturn(new Breed());

    BreedResponse result = breedService.create(postBreed);

    assertNotNull(result);
    verify(breedRepository).save(any(Breed.class));
  }

  @Test
  public void
      create_breed_with_non_existing_pet_type_throws_exception_and_has_correct_exception_message() {
    PostBreed postBreed = TestDataFactory.getPostBreed();

    when(petTypeRepository.findById(postBreed.petTypeId())).thenReturn(Optional.empty());

    BadRequestException exception =
        assertThrows(BadRequestException.class, () -> breedService.create(postBreed));

    assertEquals(NON_EXISTENT_PET_TYPE, exception.getMessage());
  }

  @Test
  public void get_non_existent_breed_by_id_throws_not_found_exception() {
    when(breedRepository.findById(INVALID_ID)).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> breedService.getById(INVALID_ID));
  }

  @Test
  public void patch_breed_with_invalid_id_throws_not_found_exception() {
    PatchBreed patch = TestDataFactory.getPatchBreed();

    when(breedRepository.findById(INVALID_ID)).thenReturn(Optional.empty());
    assertThrows(NotFoundException.class, () -> breedService.patch(INVALID_ID, patch));
  }

  @Test
  public void
      patch_breed_with_invalid_pet_type_id_should_throw_bad_request_exception_with_correct_message() {
    Breed breed = TestDataFactory.getBreed();
    PatchBreed patch = TestDataFactory.getPatchBreed(INVALID_ID);

    when(breedRepository.findById(VALID_ID)).thenReturn(Optional.of(breed));
    when(petTypeRepository.findById(INVALID_ID)).thenReturn(Optional.empty());
    BadRequestException exception =
        assertThrows(
            BadRequestException.class,
            () -> breedService.patch(VALID_ID, patch)); // TODO WERKT DIT NOG?
    assertEquals(NON_EXISTENT_PET_TYPE, exception.getMessage());
  }

  @Test
  public void patch_breed_should_return_patched_breed() {
    Breed breed = TestDataFactory.getBreed();
    PetType petType = TestDataFactory.getPetType();
    PatchBreed patch = TestDataFactory.getPatchBreed();

    when(breedRepository.findById(VALID_ID)).thenReturn(Optional.of(breed));
    when(petTypeRepository.findById(VALID_ID)).thenReturn(Optional.of(petType));

    BreedResponse result = breedService.patch(VALID_ID, patch);

    assertEquals(patch.name(), result.name());
    assertEquals(patch.petTypeId(), result.petType().id());
  }

  @Test
  public void delete_by_id_with_invalid_id_throws_not_found_exception() {
    when(breedRepository.findById(INVALID_ID)).thenReturn(Optional.empty());
    assertThrows(NotFoundException.class, () -> breedService.deleteById(INVALID_ID));
  }
}

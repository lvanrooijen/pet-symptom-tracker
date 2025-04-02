package com.laila.pet_symptom_tracker.entities.diseaselog;

import static com.laila.pet_symptom_tracker.exceptions.ExceptionMessages.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.laila.pet_symptom_tracker.TestDataFactory;
import com.laila.pet_symptom_tracker.entities.authentication.AuthenticationService;
import com.laila.pet_symptom_tracker.entities.disease.Disease;
import com.laila.pet_symptom_tracker.entities.disease.DiseaseRepository;
import com.laila.pet_symptom_tracker.entities.diseaselog.dto.DiseaseLogResponse;
import com.laila.pet_symptom_tracker.entities.diseaselog.dto.PostDiseaseLog;
import com.laila.pet_symptom_tracker.entities.pet.Pet;
import com.laila.pet_symptom_tracker.entities.pet.PetRepository;
import com.laila.pet_symptom_tracker.entities.user.User;
import com.laila.pet_symptom_tracker.exceptions.generic.BadRequestException;
import com.laila.pet_symptom_tracker.exceptions.generic.ForbiddenException;
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
class DiseaseLogServiceTest {
  @Mock DiseaseLogRepository diseaseLogRepository;
  @Mock DiseaseRepository diseaseRepository;
  @Mock PetRepository petRepository;
  @Mock AuthenticationService authenticationService;

  @InjectMocks DiseaseLogService diseaseLogService;

  @Test
  public void
      create_disease_log_with_invalid_pet_id_should_throw_bad_request_exception_with_message() {
    PostDiseaseLog requestBody = TestDataFactory.getPostDiseaseLog();
    User user = TestDataFactory.getRegularUser();

    when(authenticationService.getAuthenticatedUser()).thenReturn(user);
    when(petRepository.findById(requestBody.petId())).thenReturn(Optional.empty());

    BadRequestException exception =
        assertThrows(BadRequestException.class, () -> diseaseLogService.create(requestBody));
    assertEquals(NON_EXISTENT_PET_TYPE, exception.getMessage());
  }

  @Test
  public void create_when_not_owner_should_throw_forbidden_exception_with_message() {
    User user = TestDataFactory.getRegularUser();
    PostDiseaseLog requestBody = TestDataFactory.getPostDiseaseLog();
    Pet pet = TestDataFactory.getPet();

    when(authenticationService.getAuthenticatedUser()).thenReturn(user);
    when(petRepository.findById(requestBody.petId())).thenReturn(Optional.of(pet));

    ForbiddenException exception =
        assertThrows(ForbiddenException.class, () -> diseaseLogService.create(requestBody));

    assertEquals(OWNER_OR_ADMIN_ONLY_ACTION, exception.getMessage());
  }

  @Test
  public void
      create_disease_log_with_invalid_disease_should_throw_bad_request_exception_with_message() {
    User user = TestDataFactory.getRegularUser();
    PostDiseaseLog requestBody = TestDataFactory.getPostDiseaseLog();
    Pet pet = TestDataFactory.getPet(user);

    when(authenticationService.getAuthenticatedUser()).thenReturn(user);
    when(petRepository.findById(requestBody.petId())).thenReturn(Optional.of(pet));
    when(diseaseRepository.findById(TestDataFactory.VALID_ID)).thenReturn(Optional.empty());

    BadRequestException exception =
        assertThrows(BadRequestException.class, () -> diseaseLogService.create(requestBody));
    assertEquals(NON_EXISTENT_DISEASE, exception.getMessage());
  }

  @Test
  public void create_disease_log_should_save_and_return_disease_log() {
    User user = TestDataFactory.getRegularUser();
    PostDiseaseLog requestBody = TestDataFactory.getPostDiseaseLog();
    Pet pet = TestDataFactory.getPet(user);
    Disease disease = TestDataFactory.getDisease();

    when(authenticationService.getAuthenticatedUser()).thenReturn(user);
    when(petRepository.findById(requestBody.petId())).thenReturn(Optional.of(pet));
    when(diseaseRepository.findById(TestDataFactory.VALID_ID)).thenReturn(Optional.of(disease));

    DiseaseLogResponse result = diseaseLogService.create(requestBody);

    assertEquals(requestBody.petId(), result.pet().id());
    assertEquals(requestBody.diseaseId(), result.disease().id());
    assertEquals(requestBody.discoveryDate(), result.discoveryDate());
  }
}

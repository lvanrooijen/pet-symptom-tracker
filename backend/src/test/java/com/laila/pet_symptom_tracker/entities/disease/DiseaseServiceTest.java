package com.laila.pet_symptom_tracker.entities.disease;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.laila.pet_symptom_tracker.entities.authentication.AuthenticationService;
import com.laila.pet_symptom_tracker.entities.authentication.Role;
import com.laila.pet_symptom_tracker.entities.disease.dto.GetDisease;
import com.laila.pet_symptom_tracker.entities.disease.dto.PatchDisease;
import com.laila.pet_symptom_tracker.entities.disease.dto.PostDisease;
import com.laila.pet_symptom_tracker.entities.user.User;
import com.laila.pet_symptom_tracker.exceptions.generic.ForbiddenException;
import com.laila.pet_symptom_tracker.exceptions.generic.NotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
class DiseaseServiceTest {
  @Mock DiseaseRepository diseaseRepository;
  @Mock AuthenticationService authenticationService;
  @InjectMocks DiseaseService diseaseService;

  Disease disease;
  GetDisease getDisease;
  PostDisease postDisease;
  PatchDisease patchDisease;
  User user;
  User moderator;
  User admin;
  Long validId;
  Long invalidId;
  UUID creatorId;
  List<Disease> diseaseList;
  List<Disease> diseaseListWithDeleted;

  @BeforeEach
  public void init() {
    validId = 1L;
    invalidId = 999L;
    creatorId = UUID.randomUUID();
    user = new User("user@gmail.com", "Password123!", "User", Role.USER);
    admin = new User("admin@gmail.com", "Password123!", "Admin", Role.ADMIN);
    moderator = new User("mod@gmail.com", "Password123!", "Moderator", Role.MODERATOR);
    disease = new Disease(validId, "Rabies", "description on rabies", false, admin);
    getDisease = new GetDisease(validId, "Rabies", "description on rabies", creatorId);
    postDisease = new PostDisease(disease.getName(), disease.getDescription());
    patchDisease = new PatchDisease("TestName", "TestDescription");
    diseaseList =
        List.of(
            new Disease(1L, "A", "description on A", false, admin),
            new Disease(2L, "B", "description on B", false, admin));
    diseaseListWithDeleted =
        List.of(
            new Disease(1L, "A", "description on A", false, admin),
            new Disease(2L, "B", "description on B", false, admin),
            new Disease(3L, "C", "description on C", true, moderator),
            new Disease(4L, "D", "description on D", true, moderator));
  }

  @Test
  public void create_disease_by_user_should_throw_forbidden_exception_with_correct_message() {
    when(authenticationService.getAuthenticatedUser()).thenReturn(user);

    ForbiddenException exception =
        assertThrows(ForbiddenException.class, () -> diseaseService.create(postDisease));

    assertEquals(
        "Only an admin or moderator is allowed to perform this action.", exception.getMessage());
  }

  @Test
  public void mod_can_create_disease() {
    when(authenticationService.getAuthenticatedUser()).thenReturn(moderator);
    GetDisease result = diseaseService.create(postDisease);
    assertNotNull(result);
    verify(diseaseRepository).save(any(Disease.class));
  }

  @Test
  public void admin_can_create_disease() {
    when(authenticationService.getAuthenticatedUser()).thenReturn(admin);
    GetDisease result = diseaseService.create(postDisease);
    assertNotNull(result);
    verify(diseaseRepository).save(any(Disease.class));
  }

  @Test
  public void create_disease_returns_right_values() {
    when(authenticationService.getAuthenticatedUser()).thenReturn(admin);
    GetDisease result = diseaseService.create(postDisease);
    assertEquals(postDisease.name(), result.name());
    assertEquals(postDisease.description(), result.description());
    verify(diseaseRepository).save(any(Disease.class));
  }

  @Test
  public void get_all_as_admin_should_show_soft_deleted_diseases() {
    when(authenticationService.getAuthenticatedUser()).thenReturn(admin);
    when(diseaseRepository.findAll()).thenReturn(diseaseListWithDeleted);
    List<GetDisease> result = diseaseService.getAll();
    assertEquals(diseaseListWithDeleted.size(), result.size());
  }

  @Test
  public void get_all_as_user_should_not_show_soft_deleted_diseases() {
    when(authenticationService.getAuthenticatedUser()).thenReturn(user);
    when(diseaseRepository.findByDeletedFalse()).thenReturn(diseaseList);
    List<GetDisease> result = diseaseService.getAll();
    assertEquals(diseaseList.size(), result.size());
  }

  @Test
  public void get_disease_with_invalid_id_should_throw_not_found_exception() {
    when(diseaseRepository.findById(invalidId)).thenReturn(Optional.empty());
    assertThrows(NotFoundException.class, () -> diseaseService.getById(invalidId));
  }

  @Test
  public void patch_disease_with_invalid_id_should_throw_not_found_exception() {
    when(diseaseRepository.findById(invalidId)).thenReturn(Optional.empty());
    assertThrows(NotFoundException.class, () -> diseaseService.update(invalidId, patchDisease));
  }

  @Test
  public void patch_disease_should_return_patched_disease() {
    when(diseaseRepository.findById(validId)).thenReturn(Optional.of(disease));
    GetDisease result = diseaseService.update(validId, patchDisease);
    assertEquals(patchDisease.name(), result.name());
    assertEquals(patchDisease.description(), result.description());
  }

  @Test
  public void delete_disease_with_invalid_id_should_throw_not_found_exception() {
    when(diseaseRepository.findById(invalidId)).thenReturn(Optional.empty());
    assertThrows(NotFoundException.class, () -> diseaseService.delete(invalidId));
  }
}

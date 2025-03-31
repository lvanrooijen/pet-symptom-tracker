package com.laila.pet_symptom_tracker.entities.breed;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.laila.pet_symptom_tracker.entities.authentication.AuthenticationService;
import com.laila.pet_symptom_tracker.entities.authentication.Role;
import com.laila.pet_symptom_tracker.entities.breed.dto.GetBreed;
import com.laila.pet_symptom_tracker.entities.breed.dto.PostBreed;
import com.laila.pet_symptom_tracker.entities.pettype.PetType;
import com.laila.pet_symptom_tracker.entities.pettype.PetTypeRepository;
import com.laila.pet_symptom_tracker.entities.pettype.dto.GetPetTypeCompact;
import com.laila.pet_symptom_tracker.entities.user.User;
import com.laila.pet_symptom_tracker.exceptions.generic.BadRequestException;
import com.laila.pet_symptom_tracker.exceptions.generic.NotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BreedServiceTest {
  @InjectMocks BreedService breedService;
  PostBreed postBreed;
  GetPetTypeCompact petTypeCompact;
  PetType petType;
  GetBreed getBreed;
  List<GetBreed> breeds;
  UUID creatorId;
  User creator;
  Long invalidId;
  @Mock private BreedRepository breedRepository;
  @Mock private PetTypeRepository petTypeRepository;
  @Mock private AuthenticationService authenticationService;

  @BeforeEach
  public void init() {
    invalidId = 999L;
    creator = new User("mog@gmail.com", "Password123!", "Moderator", Role.MODERATOR);
    creatorId = UUID.randomUUID();
    postBreed = new PostBreed("Siamese", 1L);
    petTypeCompact = new GetPetTypeCompact(5L, "Cat");
    petType = new PetType(postBreed.petTypeId(), "Cat", false, creator);
    getBreed = new GetBreed(1L, "Siamese", petTypeCompact, creatorId);
    breeds =
        List.of(
            getBreed,
            new GetBreed(2L, "Shorthair", petTypeCompact, creatorId),
            new GetBreed(3L, "Sphynx", petTypeCompact, creatorId));
  }

  @Test
  public void create_breed_return_get_breed() {
    when(petTypeRepository.findById(postBreed.petTypeId()))
        .thenReturn(Optional.ofNullable(petType));
    when(authenticationService.getAuthenticatedUser()).thenReturn(creator);
    when(breedRepository.save(any(Breed.class))).thenReturn(new Breed());

    GetBreed result = breedService.create(postBreed);

    assertNotNull(result);
    verify(breedRepository).save(any(Breed.class));
  }

  @Test
  public void
      create_breed_with_non_existing_pet_type_throws_exception_and_has_correct_exception_message() {
    when(petTypeRepository.findById(postBreed.petTypeId())).thenReturn(Optional.empty());

    BadRequestException exception =
        assertThrows(BadRequestException.class, () -> breedService.create(postBreed));

    assertEquals("Pet Type does not exist", exception.getMessage());
  }

  @Test
  public void get_non_existent_breed_by_id_throws_not_found_exception() {
    when(breedRepository.findById(invalidId)).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> breedService.getById(invalidId));
  }

  @Test
  public void delete_by_id_with_invalid_id_throws_not_found_exception() {
    when(breedRepository.findById(invalidId)).thenReturn(Optional.empty());
    assertThrows(NotFoundException.class, () -> breedService.deleteById(invalidId));
  }
}

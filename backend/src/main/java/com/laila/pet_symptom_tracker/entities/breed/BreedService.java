package com.laila.pet_symptom_tracker.entities.breed;

import com.laila.pet_symptom_tracker.entities.authentication.AuthenticationService;
import com.laila.pet_symptom_tracker.entities.breed.dto.GetBreed;
import com.laila.pet_symptom_tracker.entities.breed.dto.PatchBreed;
import com.laila.pet_symptom_tracker.entities.breed.dto.PostBreed;
import com.laila.pet_symptom_tracker.entities.pettype.PetType;
import com.laila.pet_symptom_tracker.entities.pettype.PetTypeRepository;
import com.laila.pet_symptom_tracker.entities.user.User;
import com.laila.pet_symptom_tracker.exceptions.generic.BadRequestException;
import com.laila.pet_symptom_tracker.exceptions.generic.NotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BreedService {
  private final BreedRepository breedRepository;
  private final PetTypeRepository petTypeRepository;
  private final AuthenticationService authenticationService;

  public GetBreed create(PostBreed postBreed) {
    User loggedInUser = authenticationService.getAuthenticatedUser();
    PetType type =
        petTypeRepository
            .findById(postBreed.petTypeId())
            .orElseThrow(() -> new BadRequestException("Pet Type does not exist"));
    Breed createdBreed =
        Breed.builder().name(postBreed.name()).petType(type).createdBy(loggedInUser).build();
    breedRepository.save(createdBreed);
    return GetBreed.from(createdBreed);
  }

  public List<GetBreed> getAll() {
    return breedRepository.findAll().stream().map(GetBreed::from).toList();
  }

  public GetBreed getById(Long id) {
    Breed breed = breedRepository.findById(id).orElseThrow(NotFoundException::new);
    return GetBreed.from(breed);
  }

  // TODO bijhouden wie wanneer aanpassing heeft gemaakt
  public GetBreed patch(Long id, PatchBreed patch) {
    Breed updatedBreed = breedRepository.findById(id).orElseThrow(NotFoundException::new);

    if (patch.name() != null) {
      updatedBreed.setName(patch.name());
    }

    if (patch.petTypeId() != null) {
      PetType type =
          petTypeRepository
              .findById(patch.petTypeId())
              .orElseThrow(() -> new BadRequestException("Pet Type does not exist"));
      updatedBreed.setPetType(type);
    }

    return GetBreed.from(updatedBreed);
  }

  public void deleteById(Long id) {
    breedRepository.findById(id).orElseThrow(NotFoundException::new);
    breedRepository.deleteById(id);
  }
}

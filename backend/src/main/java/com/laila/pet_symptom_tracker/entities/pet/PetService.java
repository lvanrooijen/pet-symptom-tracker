package com.laila.pet_symptom_tracker.entities.pet;

import com.laila.pet_symptom_tracker.entities.authentication.AuthenticationService;
import com.laila.pet_symptom_tracker.entities.breed.Breed;
import com.laila.pet_symptom_tracker.entities.breed.BreedRepository;
import com.laila.pet_symptom_tracker.entities.pet.dto.GetPet;
import com.laila.pet_symptom_tracker.entities.pet.dto.PatchPet;
import com.laila.pet_symptom_tracker.entities.pet.dto.PostPet;
import com.laila.pet_symptom_tracker.entities.user.User;
import com.laila.pet_symptom_tracker.entities.user.UserRepository;
import com.laila.pet_symptom_tracker.exceptions.generic.BadRequestException;
import com.laila.pet_symptom_tracker.exceptions.generic.ForbiddenException;
import com.laila.pet_symptom_tracker.exceptions.generic.NoContentException;
import com.laila.pet_symptom_tracker.exceptions.generic.NotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PetService {
  private final PetRepository petRepository;
  private final BreedRepository breedRepository;
  private final UserRepository userRepository;
  private final AuthenticationService authenticationService;

  public GetPet create(PostPet dto) {
    User loggedInUser = authenticationService.getAuthenticatedUser();
    Breed breed =
        breedRepository
            .findById(dto.breedId())
            .orElseThrow(() -> new BadRequestException("Breed not found"));

    Pet pet =
        Pet.builder()
            .name(dto.name())
            .owner(loggedInUser)
            .dateOfBirth(dto.dateOfBirth())
            .breed(breed)
            .isAlive(true)
            .build();

    return GetPet.from(petRepository.save(pet));
  }

  public GetPet getById(Long id) {
    User loggedInUser = authenticationService.getAuthenticatedUser();
    Pet pet = petRepository.findById(id).orElseThrow(() -> new NotFoundException("Pet not found"));
    if (pet.isOwner(loggedInUser)
        || loggedInUser.hasAdminRole()
        || loggedInUser.hasModeratorRole()) {
      return GetPet.from(pet);
    } else {
      throw new ForbiddenException();
    }
  }

  public List<GetPet> getAll() {
    User loggedInUser = authenticationService.getAuthenticatedUser();
    List<Pet> pets;

    if (loggedInUser.hasAdminRole() || loggedInUser.hasModeratorRole()) {
      pets = petRepository.findAll();
    } else {
      pets = petRepository.findByOwnerId(loggedInUser.getId());
    }

    if (pets.isEmpty()) throw new NoContentException();

    return pets.stream().map(GetPet::from).toList();
  }

  public GetPet update(Long id, PatchPet patch) {
    User loggedInUser = authenticationService.getAuthenticatedUser();
    Pet updatedPet = petRepository.findById(id).orElseThrow(NotFoundException::new);

    if (updatedPet.isNotOwner(loggedInUser)) {
      throw new ForbiddenException("You don't have permission to update this pet");
    }

    if (patch.userId() != null) {
      User newOwner = userRepository.findById(patch.userId()).orElseThrow(NotFoundException::new);
      updatedPet.setOwner(newOwner);
    }
    if (patch.name() != null) {
      updatedPet.setName(patch.name());
    }
    if (patch.dateOfBirth() != null) {
      updatedPet.setDateOfBirth(patch.dateOfBirth());
    }
    if (patch.isAlive() != null) {
      updatedPet.setIsAlive(patch.isAlive());
    }
    if (patch.dateOfDeath() != null) {
      updatedPet.setDateOfDeath(patch.dateOfDeath());
    }

    if (patch.breedId() != null) {
      Breed breed =
          breedRepository
              .findById(patch.breedId())
              .orElseThrow(() -> new BadRequestException("Breed does not exist"));
      updatedPet.setBreed(breed);
    }

    petRepository.save(updatedPet);
    return GetPet.from(updatedPet);
  }

  public void delete(Long id) {
    User loggedInUser = authenticationService.getAuthenticatedUser();
    Pet pet = petRepository.findById(id).orElseThrow(NotFoundException::new);

    if (pet.isOwner(loggedInUser) || loggedInUser.hasAdminRole()) {
      petRepository.deleteById(id);
    } else {
      throw new ForbiddenException();
    }
  }
}

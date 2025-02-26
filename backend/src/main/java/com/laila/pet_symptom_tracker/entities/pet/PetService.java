package com.laila.pet_symptom_tracker.entities.pet;

import com.laila.pet_symptom_tracker.entities.pet.dto.GetPet;
import com.laila.pet_symptom_tracker.entities.pet.dto.PatchPet;
import com.laila.pet_symptom_tracker.entities.pet.dto.PostPet;
import com.laila.pet_symptom_tracker.entities.user.User;
import com.laila.pet_symptom_tracker.entities.user.UserRepository;
import com.laila.pet_symptom_tracker.exceptions.generic.ForbiddenException;
import com.laila.pet_symptom_tracker.exceptions.generic.NoContentException;
import com.laila.pet_symptom_tracker.exceptions.generic.NotFoundException;
import com.laila.pet_symptom_tracker.mainconfig.ColoredLogger;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PetService {
  private final PetRepository petRepository;
  private final UserRepository userRepository;

  public GetPet create(User loggedInUser, PostPet dto) {
    Pet pet = PostPet.to(dto);
    pet.setOwner(loggedInUser);
    return GetPet.from(petRepository.save(pet));
  }

  public GetPet getById(User loggedInUser, Long id) {
    Pet pet = petRepository.findById(id).orElseThrow(() -> new NotFoundException("Pet not found"));
    if (pet.isOwner(loggedInUser) || loggedInUser.isAdmin() || loggedInUser.isModerator()) {
      return GetPet.from(pet);
    } else {
      throw new ForbiddenException();
    }
  }

  public List<GetPet> getAll(User loggedInUser) {
    List<Pet> pets;

    if (loggedInUser.isAdmin() || loggedInUser.isModerator()) {
      pets = petRepository.findAll();
    } else {
      pets = petRepository.findByOwnerId(loggedInUser.getId());
    }

    if (pets.isEmpty()) throw new NoContentException();

    return pets.stream().map(GetPet::from).toList();
  }

  public GetPet update(User loggedInUser, Long id, PatchPet patch) {
    Pet updatedPet = petRepository.findById(id).orElseThrow(NotFoundException::new);

    ColoredLogger.logWarning("Entering update method in pet service");

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

    petRepository.save(updatedPet);
    return GetPet.from(updatedPet);
  }

  public void delete(User loggedInUser, Long id) {
    Pet pet = petRepository.findById(id).orElseThrow(NotFoundException::new);

    if (pet.isOwner(loggedInUser) || loggedInUser.isAdmin()) {
      petRepository.deleteById(id);
    } else {
      throw new ForbiddenException();
    }
  }
}

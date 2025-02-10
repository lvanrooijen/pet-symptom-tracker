package com.laila.pet_symptom_tracker.entities.pet;

import com.laila.pet_symptom_tracker.entities.pet.dto.GetPet;
import com.laila.pet_symptom_tracker.entities.pet.dto.PatchPet;
import com.laila.pet_symptom_tracker.entities.pet.dto.PostPet;
import com.laila.pet_symptom_tracker.entities.user.User;
import com.laila.pet_symptom_tracker.entities.user.UserRepository;
import com.laila.pet_symptom_tracker.exceptions.ForbiddenException;
import com.laila.pet_symptom_tracker.exceptions.NoContentException;
import com.laila.pet_symptom_tracker.exceptions.NotFoundException;
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
    pet.setUser(loggedInUser);
    return GetPet.from(petRepository.save(pet));
  }

  public GetPet getById(User loggedInUser, Long id) {
    Pet pet = petRepository.findById(id).orElseThrow(() -> new NotFoundException("Pet not found"));
    if (loggedInUser.equals(pet.getUser())
        || loggedInUser.isAdmin()
        || loggedInUser.isModerator()) {
      return GetPet.from(pet);
    } else {
      throw new ForbiddenException();
    }
  }

  public List<GetPet> getAll(User loggedInUser) {
    if (loggedInUser.isUser()) throw new ForbiddenException();

    List<GetPet> pets = petRepository.findAll().stream().map(GetPet::from).toList();
    if (pets.isEmpty()) throw new NoContentException();

    return pets;
  }

  public GetPet update(User loggedInUser, Long id, PatchPet patch) {
    if (loggedInUser.isUser() || loggedInUser.isModerator() || loggedInUser.isAdmin()) {
      Pet updatedPet = petRepository.findById(id).orElseThrow(NotFoundException::new);

      if (patch.userId() != null) {
        User newOwner = userRepository.findById(patch.userId()).orElseThrow(NotFoundException::new);
        updatedPet.setUser(newOwner);
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
    } else throw new ForbiddenException();
  }

  public void delete(User loggedInUser, Long id) {
    Pet pet = petRepository.findById(id).orElseThrow(NotFoundException::new);

    if (!pet.getUser().equals(loggedInUser) || !loggedInUser.isAdmin())
      throw new ForbiddenException();

    petRepository.deleteById(id);
  }
}

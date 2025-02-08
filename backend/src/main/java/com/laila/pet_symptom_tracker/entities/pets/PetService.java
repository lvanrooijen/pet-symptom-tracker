package com.laila.pet_symptom_tracker.entities.pets;

import com.laila.pet_symptom_tracker.entities.pets.dto.GetPet;
import com.laila.pet_symptom_tracker.entities.pets.dto.PatchPet;
import com.laila.pet_symptom_tracker.entities.pets.dto.PostPet;
import com.laila.pet_symptom_tracker.entities.user.User;
import com.laila.pet_symptom_tracker.exceptions.BadRequestException;
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

  public GetPet create(User user, PostPet dto) {
    // als user een admin/mod is, wil ik dan andere opties geven?
    Pet pet = PostPet.to(dto);
    pet.setUser(user);
    return GetPet.from(petRepository.save(pet));
  }

  public GetPet getById(User loggedInUser, Long id) {
    // wie mag toegang onder welke voorwaarden?
    Pet pet =
        petRepository.findById(id).orElseThrow(() -> new BadRequestException("Pet not found"));

    return GetPet.from(pet);
  }

  public List<GetPet> getAll(User loggedInUser) {
    if (loggedInUser.isUser()) throw new ForbiddenException();

    List<GetPet> pets = petRepository.findAll().stream().map(GetPet::from).toList();
    if (pets.isEmpty()) throw new NoContentException();

    return pets;
  }

  public GetPet update(User loggedInUser, Long id, PatchPet patch) {
    // of user moet owner zijn, of admin/mod mag
    Pet updatedPet = petRepository.findById(id).orElseThrow(NotFoundException::new);

    if (patch.userId() != null) {
      // find user and set it to this pet
    }
  }

  public void delete(User loggedInUser, Long id) {
    // owner admin/mod mag verwijderen
  }
}

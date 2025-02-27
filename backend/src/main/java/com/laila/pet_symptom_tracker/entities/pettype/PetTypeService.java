package com.laila.pet_symptom_tracker.entities.pettype;

import com.laila.pet_symptom_tracker.entities.pettype.dto.GetPetType;
import com.laila.pet_symptom_tracker.entities.pettype.dto.PatchPetType;
import com.laila.pet_symptom_tracker.entities.pettype.dto.PostPetType;
import com.laila.pet_symptom_tracker.entities.user.User;
import com.laila.pet_symptom_tracker.exceptions.generic.ForbiddenException;
import com.laila.pet_symptom_tracker.exceptions.generic.NotFoundException;
import com.laila.pet_symptom_tracker.mainconfig.ColoredLogger;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PetTypeService {
  private final PetTypeRepository petTypeRepository;

  public GetPetType create(PostPetType dto, User loggedInUser) {
    ColoredLogger.prettyInPink(loggedInUser.getRole().toString());
    if (!loggedInUser.isModerator() && !loggedInUser.isAdmin())
      throw new ForbiddenException(
          "You must be an administrator or a moderator to perform this action");

    PetType createdType = PostPetType.to(dto);
    ColoredLogger.prettyInPink(createdType.getName());

    petTypeRepository.save(createdType);

    return GetPetType.from(createdType);
  }

  public List<GetPetType> getAll() {
    return petTypeRepository.findAll().stream().map(GetPetType::from).toList();
  }

  public GetPetType getById(Long id) {
    PetType petType = petTypeRepository.findById(id).orElseThrow(NotFoundException::new);
    return GetPetType.from(petType);
  }

  public GetPetType patch(Long id, PatchPetType patch) {
    PetType petType = petTypeRepository.findById(id).orElseThrow(NotFoundException::new);

    if (patch.name() != null) {
      petType.setName(patch.name());
    }

    petTypeRepository.save(petType);
    return GetPetType.from(petType);
  }

  public void deleteById(Long id) {
    petTypeRepository.findById(id).orElseThrow(NotFoundException::new);
    petTypeRepository.deleteById(id);
  }
}

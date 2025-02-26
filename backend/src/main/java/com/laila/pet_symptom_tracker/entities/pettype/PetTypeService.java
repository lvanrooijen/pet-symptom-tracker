package com.laila.pet_symptom_tracker.entities.pettype;

import com.laila.pet_symptom_tracker.entities.pettype.dto.PostPetType;
import com.laila.pet_symptom_tracker.entities.user.User;
import com.laila.pet_symptom_tracker.exceptions.generic.ForbiddenException;
import com.laila.pet_symptom_tracker.mainconfig.ColoredLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PetTypeService {
  private final PetTypeRepository petTypeRepository;

  public PetType create(PostPetType dto, User loggedInUser) {
    ColoredLogger.prettyInPink(loggedInUser.getRole().toString());
    if (!loggedInUser.isModerator() && !loggedInUser.isAdmin())
      throw new ForbiddenException(
          "You must be an administrator or a moderator to perform this action");

    PetType createdType = PostPetType.to(dto);
    ColoredLogger.prettyInPink(createdType.getName());
    return petTypeRepository.save(createdType);
  }
}

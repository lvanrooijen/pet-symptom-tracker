package com.laila.pet_symptom_tracker.entities.symptomlog;

import com.laila.pet_symptom_tracker.entities.pet.Pet;
import com.laila.pet_symptom_tracker.entities.pet.PetRepository;
import com.laila.pet_symptom_tracker.entities.symptom.Symptom;
import com.laila.pet_symptom_tracker.entities.symptom.SymptomRepository;
import com.laila.pet_symptom_tracker.entities.symptomlog.dto.GetSymptomLog;
import com.laila.pet_symptom_tracker.entities.symptomlog.dto.PostSymptomLog;
import com.laila.pet_symptom_tracker.entities.user.User;
import com.laila.pet_symptom_tracker.exceptions.generic.BadRequestException;
import com.laila.pet_symptom_tracker.exceptions.generic.ForbiddenException;
import com.laila.pet_symptom_tracker.exceptions.generic.NotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SymptomLogService {
  private final SymptomLogRepository symptomLogRepository;
  private final SymptomRepository symptomRepository;
  private final PetRepository petRepository;

  public GetSymptomLog create(User loggedInUser, PostSymptomLog postBody) {
    Pet pet =
        petRepository
            .findById(postBody.petId())
            .orElseThrow(() -> new BadRequestException("Pet does not exist"));

    if (pet.isNotOwner(loggedInUser)) {
      throw new ForbiddenException("Logs for pets can only be created by their owners");
    }

    Symptom symptom =
        symptomRepository
            .findById(postBody.symptomId())
            .orElseThrow(() -> new BadRequestException("Symptom does not exist"));

    SymptomLog createdSymptomLog =
        SymptomLog.builder()
            .pet(pet)
            .symptom(symptom)
            .details(postBody.details())
            .reportDate(postBody.reportDate())
            .reportTime(postBody.reportTime())
            .build();

    symptomLogRepository.save(createdSymptomLog);
    return GetSymptomLog.from(createdSymptomLog);
  }

  public List<GetSymptomLog> findAll(User loggedInUser) {
    // TODO me!
    return null;
  }

  public GetSymptomLog getById(User loggedInUser, Long id) {
    SymptomLog log = symptomLogRepository.findById(id).orElseThrow(NotFoundException::new);
    Pet pet = log.pet;

    if (pet.isOwner(loggedInUser)
        || loggedInUser.hasAdminRole()
        || loggedInUser.hasModeratorRole()) {
      return GetSymptomLog.from(log);

    } else {
      throw new ForbiddenException("Forbidden");
    }
  }

  public GetSymptomLog update() {
    // TODO me!
    return null;
  }

  public void delete() {
    // TODO me!
  }
}

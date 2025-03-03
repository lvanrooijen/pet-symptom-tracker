package com.laila.pet_symptom_tracker.entities.symptom;

import com.laila.pet_symptom_tracker.entities.symptom.dto.GetSymptom;
import com.laila.pet_symptom_tracker.entities.symptom.dto.PatchSymptom;
import com.laila.pet_symptom_tracker.entities.symptom.dto.PostSymptom;
import com.laila.pet_symptom_tracker.entities.user.User;
import com.laila.pet_symptom_tracker.exceptions.generic.ForbiddenException;
import com.laila.pet_symptom_tracker.exceptions.generic.NotFoundException;
import com.laila.pet_symptom_tracker.mainconfig.ColoredLogger;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SymptomService {
  private final SymptomRepository symptomRepository;

  public GetSymptom create(PostSymptom postSymptom, User loggedInUser) {
    Symptom created =
        Symptom.builder()
            .name(postSymptom.name())
            .description(postSymptom.description())
            .isVerified(loggedInUser.isAdmin() || loggedInUser.isModerator())
            .build();

    symptomRepository.save(created);
    return GetSymptom.from(created);
  }

  public List<GetSymptom> getAll(User loggedInUser) {
    List<Symptom> symptoms;
    if (loggedInUser.isAdmin()) {
      symptoms = symptomRepository.findAll();
    } else {
      symptoms = symptomRepository.findByDeletedFalse();
    }
    return symptoms.stream().map(GetSymptom::from).toList();
  }

  public GetSymptom getById(Long id) {
    Symptom symptom = symptomRepository.findById(id).orElseThrow(NotFoundException::new);
    return GetSymptom.from(symptom);
  }

  public GetSymptom update(Long id, PatchSymptom patch, User loggedInUser) {
    Symptom symptom = symptomRepository.findById(id).orElseThrow(NotFoundException::new);

    if (patch.isVerified() != null) {
      if (loggedInUser.isUser()) {
        throw new ForbiddenException("Only an administrator or moderator can perform this action");
      }
      if (loggedInUser.isAdmin() || loggedInUser.isModerator()) {
        ColoredLogger.prettyInPink(
            "is admin:  " + loggedInUser.isAdmin() + " is Mod: " + loggedInUser.isModerator());
        symptom.setIsVerified(patch.isVerified());
      }
    }

    if (patch.name() != null) {
      symptom.setName(patch.name());
    }

    if (patch.description() != null) {
      symptom.setDescription(patch.description());
    }

    symptomRepository.save(symptom);
    return GetSymptom.from(symptom);
  }

  public void delete(Long id) {
    symptomRepository.findById(id).orElseThrow(NotFoundException::new);
    symptomRepository.deleteById(id);
  }
}

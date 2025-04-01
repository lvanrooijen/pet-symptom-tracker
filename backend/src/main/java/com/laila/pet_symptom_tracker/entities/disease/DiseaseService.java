package com.laila.pet_symptom_tracker.entities.disease;

import static com.laila.pet_symptom_tracker.exceptions.GenericExceptionMessages.onlyAdminAndModCanPerformAction;

import com.laila.pet_symptom_tracker.entities.authentication.AuthenticationService;
import com.laila.pet_symptom_tracker.entities.disease.dto.GetDisease;
import com.laila.pet_symptom_tracker.entities.disease.dto.PatchDisease;
import com.laila.pet_symptom_tracker.entities.disease.dto.PostDisease;
import com.laila.pet_symptom_tracker.entities.user.User;
import com.laila.pet_symptom_tracker.exceptions.generic.ForbiddenException;
import com.laila.pet_symptom_tracker.exceptions.generic.NotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiseaseService {
  private final DiseaseRepository diseaseRepository;
  private final AuthenticationService authenticationService;

  public GetDisease create(PostDisease body) {
    User loggedInUser = authenticationService.getAuthenticatedUser();
    if (loggedInUser.hasUserRole()) {
      throw new ForbiddenException(onlyAdminAndModCanPerformAction);
    }
    Disease createdDisease =
        Disease.builder()
            .name(body.name())
            .description(body.description())
            .createdBy(loggedInUser)
            .build();

    diseaseRepository.save(createdDisease);

    return GetDisease.from(createdDisease);
  }

  public List<GetDisease> getAll() {
    User loggedInUser = authenticationService.getAuthenticatedUser();
    List<Disease> diseases;
    if (loggedInUser.hasAdminRole()) {
      diseases = diseaseRepository.findAll();
    } else {
      diseases = diseaseRepository.findByDeletedFalse();
    }
    return diseases.stream().map(GetDisease::from).toList();
  }

  public GetDisease getById(Long id) {
    Disease disease = diseaseRepository.findById(id).orElseThrow(NotFoundException::new);
    return GetDisease.from(disease);
  }

  public GetDisease update(Long id, PatchDisease patch) {
    Disease updatedDisease = diseaseRepository.findById(id).orElseThrow(NotFoundException::new);
    if (patch.name() != null) {
      updatedDisease.setName(patch.name());
    }
    if (patch.description() != null) {
      updatedDisease.setDescription(patch.description());
    }
    diseaseRepository.save(updatedDisease);
    return GetDisease.from(updatedDisease);
  }

  public void delete(Long id) {
    diseaseRepository.findById(id).orElseThrow(NotFoundException::new);
    diseaseRepository.deleteById(id);
  }
}

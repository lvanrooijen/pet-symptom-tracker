package com.laila.pet_symptom_tracker.entities.disease;

import com.laila.pet_symptom_tracker.entities.disease.dto.GetDisease;
import com.laila.pet_symptom_tracker.entities.disease.dto.PatchDisease;
import com.laila.pet_symptom_tracker.entities.disease.dto.PostDisease;
import com.laila.pet_symptom_tracker.entities.user.User;
import com.laila.pet_symptom_tracker.exceptions.generic.NotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiseaseService {
  private final DiseaseRepository diseaseRepository;

  public GetDisease create(PostDisease body, User loggedInUser) {
    Disease createdDisease =
        Disease.builder()
            .name(body.name())
            .description(body.description())
            .createdBy(loggedInUser)
            .build();

    diseaseRepository.save(createdDisease);

    return GetDisease.from(createdDisease);
  }

  public List<GetDisease> getAll(User loggedInUser) {
    List<Disease> diseases;
    if (loggedInUser.isAdmin()) {
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

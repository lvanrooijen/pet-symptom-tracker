package com.laila.pet_symptom_tracker.entities.diseaselog;

import com.laila.pet_symptom_tracker.entities.disease.Disease;
import com.laila.pet_symptom_tracker.entities.disease.DiseaseRepository;
import com.laila.pet_symptom_tracker.entities.diseaselog.dto.GetDiseaseLog;
import com.laila.pet_symptom_tracker.entities.diseaselog.dto.PatchDiseaseLog;
import com.laila.pet_symptom_tracker.entities.diseaselog.dto.PostDiseaseLog;
import com.laila.pet_symptom_tracker.entities.pet.Pet;
import com.laila.pet_symptom_tracker.entities.pet.PetRepository;
import com.laila.pet_symptom_tracker.entities.user.User;
import com.laila.pet_symptom_tracker.exceptions.generic.ForbiddenException;
import com.laila.pet_symptom_tracker.exceptions.generic.NotFoundException;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiseaseLogService {
  private final DiseaseLogRepository diseaseLogRepository;
  private final DiseaseRepository diseaseRepository;
  private final PetRepository petRepository;

  public GetDiseaseLog create(PostDiseaseLog postBody, User loggedInUser) {
    Pet pet =
        petRepository
            .findById(postBody.petId())
            .orElseThrow(() -> new NotFoundException("Pet does not exist"));

    if (pet.isNotOwner(loggedInUser) && loggedInUser.hasUserRole()) {
      throw new ForbiddenException("Only the owner or admin is allowed to perform this action");
    }

    Disease disease =
        diseaseRepository
            .findById(postBody.diseaseId())
            .orElseThrow(() -> new NotFoundException("Disease does not exist"));

    DiseaseLog createdDiseaseLog =
        DiseaseLog.builder()
            .pet(pet)
            .disease(disease)
            .discoveryDate(
                postBody.discoveryDate() == null ? LocalDate.now() : postBody.discoveryDate())
            .isHealed(false)
            .build();

    diseaseLogRepository.save(createdDiseaseLog);

    return GetDiseaseLog.from(createdDiseaseLog);
  }

  public List<GetDiseaseLog> getAll(User loggedInUser) {
    List<DiseaseLog> diseaseLogs = diseaseLogRepository.findAll();

    if (loggedInUser.hasUserRole()) {
      return diseaseLogs.stream()
          .filter(log -> log.getPet().isOwner(loggedInUser))
          .map(GetDiseaseLog::from)
          .toList();
    }

    return diseaseLogs.stream().map(GetDiseaseLog::from).toList();
  }

  public GetDiseaseLog getById(Long id, User loggedInUser) {
    DiseaseLog diseaseLog = diseaseLogRepository.findById(id).orElseThrow(NotFoundException::new);
    if (diseaseLog.getPet().isOwner(loggedInUser)
        || loggedInUser.hasModeratorRole()
        || loggedInUser.hasAdminRole()) {
      return GetDiseaseLog.from(diseaseLog);
    } else {
      throw new ForbiddenException("Only the owner or admin may perform this action.");
    }
  }

  public GetDiseaseLog update(Long id, PatchDiseaseLog patch, User loggedInUser) {
    DiseaseLog update = diseaseLogRepository.findById(id).orElseThrow(NotFoundException::new);

    // TODO bug hier!
    if (update.getPet().isNotOwner(loggedInUser) && loggedInUser.hasUserRole()) {
      throw new ForbiddenException("Only the owner or admin is allowed to perform this action");
    }

    if (patch.isHealed() != null) {
      update.setIsHealed(patch.isHealed());
    }
    if (patch.healedOnDate() != null) {
      update.setHealedOnDate(patch.healedOnDate());
    }
    if (patch.diseaseId() != null) {
      Disease disease =
          diseaseRepository
              .findById(patch.diseaseId())
              .orElseThrow(() -> new NotFoundException("Disease not found"));
      update.setDisease(disease);
    }
    if (patch.discoveryDate() != null) {
      update.setDiscoveryDate(patch.discoveryDate());
    }
    diseaseLogRepository.save(update);
    return GetDiseaseLog.from(update);
  }

  public void delete(Long id, User loggedInUser) {
    DiseaseLog log = diseaseLogRepository.findById(id).orElseThrow(NotFoundException::new);
    if (log.getPet().isNotOwner(loggedInUser) && loggedInUser.hasUserRole()) {
      throw new ForbiddenException("Only the owner or admin may perform this action");
    }
    diseaseLogRepository.deleteById(id);
  }
}

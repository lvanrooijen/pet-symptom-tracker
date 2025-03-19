package com.laila.pet_symptom_tracker.entities.diseaselog.dto;

import com.laila.pet_symptom_tracker.entities.disease.dto.GetDiseaseCompact;
import com.laila.pet_symptom_tracker.entities.diseaselog.DiseaseLog;
import com.laila.pet_symptom_tracker.entities.pet.dto.GetPetCompact;
import java.time.LocalDate;

public record GetDiseaseLog(
    Long id,
    GetDiseaseCompact disease,
    GetPetCompact pet,
    LocalDate discoveryDate,
    Boolean isHealed,
    LocalDate healedOnDate) {
  public static GetDiseaseLog from(DiseaseLog entity) {
    return new GetDiseaseLog(
        entity.getId(),
        GetDiseaseCompact.from(entity.getDisease()),
        GetPetCompact.from(entity.getPet()),
        entity.getDiscoveryDate(),
        entity.getIsHealed(),
        entity.getHealedOnDate());
  }
}

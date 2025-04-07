package com.laila.pet_symptom_tracker.entities.symptomlog;

import com.laila.pet_symptom_tracker.entities.pet.Pet;
import com.laila.pet_symptom_tracker.entities.symptom.Symptom;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
public class SymptomLog {
  @ManyToOne @Setter Pet pet;
  @Id @GeneratedValue private Long id;
  @ManyToOne @Setter private Symptom symptom;

  @Column(nullable = true)
  @Setter
  private String details;

  @Column(nullable = false)
  @Setter
  private LocalDate reportDate;

  @Builder
  private SymptomLog(Pet pet, Symptom symptom, String details, LocalDate reportDate) {
    this.pet = pet;
    this.symptom = symptom;
    this.details = details;
    this.reportDate = reportDate;
  }

  public SymptomLog(Long id, Pet pet, Symptom symptom, String details, LocalDate reportDate) {
    this.id = id;
    this.pet = pet;
    this.symptom = symptom;
    this.details = details;
    this.reportDate = reportDate;
  }
}

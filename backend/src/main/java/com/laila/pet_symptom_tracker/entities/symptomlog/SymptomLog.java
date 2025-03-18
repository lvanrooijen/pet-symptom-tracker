package com.laila.pet_symptom_tracker.entities.symptomlog;

import com.laila.pet_symptom_tracker.entities.pet.Pet;
import com.laila.pet_symptom_tracker.entities.symptom.Symptom;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class SymptomLog {
  @ManyToOne Pet pet;
  @Id @GeneratedValue private Long id;
  @ManyToOne private Symptom symptom;

  @Column(nullable = true)
  private String details;

  @Column(nullable = false)
  private LocalDate reportDate;

  @Column(nullable = true)
  private LocalTime reportTime;

  @Builder
  private SymptomLog(
      Pet pet, Symptom symptom, String details, LocalDate reportDate, LocalTime reportTime) {
    this.pet = pet;
    this.symptom = symptom;
    this.details = details;
    this.reportDate = reportDate;
    this.reportTime = reportTime;
  }
}

package com.laila.pet_symptom_tracker.entities.diseaselog;

import com.laila.pet_symptom_tracker.entities.disease.Disease;
import com.laila.pet_symptom_tracker.entities.pet.Pet;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
public class DiseaseLog {
  @Id @GeneratedValue private Long id;

  @ManyToOne @Setter private Disease disease;
  @ManyToOne private Pet pet;

  @Column(nullable = false)
  @Setter
  private LocalDate discoveryDate;

  @Column(nullable = true)
  @Setter
  private LocalDate healedOnDate;

  @Column(nullable = false)
  @Setter
  private Boolean isHealed;

  @Builder
  private DiseaseLog(
      Disease disease, Pet pet, LocalDate discoveryDate, LocalDate healedOnDate, Boolean isHealed) {
    this.disease = disease;
    this.pet = pet;
    this.discoveryDate = discoveryDate;
    this.healedOnDate = healedOnDate;
    this.isHealed = isHealed;
  }
}

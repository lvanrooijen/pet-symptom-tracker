package com.laila.pet_symptom_tracker.entities.diseaselog;

import com.laila.pet_symptom_tracker.entities.disease.Disease;
import com.laila.pet_symptom_tracker.entities.pet.Pet;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class DiseaseLog {
  @Id @GeneratedValue private Long id;

  @ManyToOne private Disease disease;
  @ManyToOne private Pet pet;

  @Column(nullable = false)
  private LocalDate discoveredOnDate;

  @Column(nullable = true)
  private LocalDate healedOnDate;

  @Column(nullable = false)
  private Boolean isHealed;
}

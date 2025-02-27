package com.laila.pet_symptom_tracker.entities.symptomlog;

import com.laila.pet_symptom_tracker.entities.symptom.Symptom;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class SymptomLog {
  @Id @GeneratedValue private Long id;

  @ManyToOne private Symptom symptom;

  @Column(nullable = false)
  private LocalDate reportedOnDate;

  @Column(nullable = true)
  private LocalTime reportedOnTime;
}

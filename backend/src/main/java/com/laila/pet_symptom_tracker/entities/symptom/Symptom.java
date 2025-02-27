package com.laila.pet_symptom_tracker.entities.symptom;

import com.laila.pet_symptom_tracker.entities.symptomtype.SymptomType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Symptom {
  @Id @GeneratedValue private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String description;

  @ManyToOne private SymptomType symptomType;
}

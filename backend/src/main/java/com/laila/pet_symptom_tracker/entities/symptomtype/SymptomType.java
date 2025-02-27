package com.laila.pet_symptom_tracker.entities.symptomtype;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class SymptomType {
  @Id @GeneratedValue private Long id;

  @Column(nullable = false)
  private String name;
}

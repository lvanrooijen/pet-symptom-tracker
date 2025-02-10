package com.laila.pet_symptom_tracker.entities.disease;

import com.laila.pet_symptom_tracker.entities.diseasetype.DiseaseType;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Disease {
  @Id @GeneratedValue private Long id;

  @Column(nullable = false)
  private String name;

  @ManyToOne private DiseaseType diseaseType;
}

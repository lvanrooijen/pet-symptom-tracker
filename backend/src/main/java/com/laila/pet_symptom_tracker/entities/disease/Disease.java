package com.laila.pet_symptom_tracker.entities.disease;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Disease {
  @Id @GeneratedValue private Long id;

  @Column(nullable = false)
  private String name;
}

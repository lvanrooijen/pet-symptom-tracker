package com.laila.pet_symptom_tracker.entities.pettype;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
public class PetType {
  @Id @GeneratedValue private Long id;

  @Column(nullable = false)
  @Setter
  private String name;

  public PetType(String name) {
    this.name = name;
  }
}

package com.laila.pet_symptom_tracker.entities.breed;

import com.laila.pet_symptom_tracker.entities.pettype.PetType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
public class Breed {
  @Id @GeneratedValue private Long id;

  @Column(nullable = false)
  @Setter
  private String name;

  @Setter @ManyToOne private PetType petType;

  public Breed(String name, PetType petType) {
    this.name = name;
    this.petType = petType;
  }
}

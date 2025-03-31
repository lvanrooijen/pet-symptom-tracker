package com.laila.pet_symptom_tracker.entities.breed;

import com.laila.pet_symptom_tracker.entities.pettype.PetType;
import com.laila.pet_symptom_tracker.entities.user.User;
import jakarta.persistence.*;
import lombok.Builder;
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

  @ManyToOne private User createdBy;

  @Builder
  private Breed(String name, PetType petType, User createdBy) {
    this.name = name;
    this.petType = petType;
    this.createdBy = createdBy;
  }

  public Breed(Long id, String name, PetType petType, User createdBy) {
    this.id = id;
    this.name = name;
    this.petType = petType;
    this.createdBy = createdBy;
  }
}

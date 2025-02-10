package com.laila.pet_symptom_tracker.entities.petbreed;

import com.laila.pet_symptom_tracker.entities.pettype.PetType;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class PetBreed {
  @Id @GeneratedValue private Long id;

  @Column(nullable = false)
  private String name;

  @ManyToOne private PetType petType;
}

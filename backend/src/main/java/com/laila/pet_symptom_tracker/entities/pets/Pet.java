package com.laila.pet_symptom_tracker.entities.pets;

import com.laila.pet_symptom_tracker.entities.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Entity(name = "pets")
@NoArgsConstructor
public class Pet {
  @ManyToOne User user;
  @Id @Generated private Long id;
  private String name;

  // PetType petType;

  // PetBreed petBreed;

  // DiseaseLog diseaseLog;
  private LocalDate dateOfBirth;

  @Column(nullable = false)
  private Boolean isAlive;

  @Column(nullable = true)
  private LocalDate dateOfDeath;
}

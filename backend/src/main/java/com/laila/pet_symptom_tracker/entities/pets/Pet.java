package com.laila.pet_symptom_tracker.entities.pets;

import com.laila.pet_symptom_tracker.entities.user.User;
import jakarta.persistence.*;

import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "pets")
@NoArgsConstructor
@Getter
public class Pet {
  @Setter @ManyToOne User user;
  @Id @GeneratedValue private Long id;
  @Setter private String name;

  // PetType petType;

  // PetBreed petBreed;

  // DiseaseLog diseaseLog;

  @Setter private LocalDate dateOfBirth;

  @Column(nullable = false)
  @Setter
  private Boolean isAlive;

  @Column(nullable = true)
  @Setter
  private LocalDate dateOfDeath;

  public Pet(String name, LocalDate dateOfBirth, Boolean isAlive) {
    this.name = name;
    this.dateOfBirth = dateOfBirth;
    this.isAlive = isAlive;
  }

  public Pet(User user, String name, LocalDate dateOfBirth, Boolean isAlive) {
    this.user = user;
    this.name = name;
    this.dateOfBirth = dateOfBirth;
    this.isAlive = isAlive;
  }
}

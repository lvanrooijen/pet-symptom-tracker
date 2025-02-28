package com.laila.pet_symptom_tracker.entities.pet;

import com.laila.pet_symptom_tracker.entities.breed.Breed;
import com.laila.pet_symptom_tracker.entities.user.User;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "pets")
@NoArgsConstructor
@Getter
@Builder
public class Pet {
  @Setter @ManyToOne User owner;
  @Id @GeneratedValue private Long id;
  @Setter private String name;

  @Setter @ManyToOne private Breed breed;

  @Setter private LocalDate dateOfBirth;

  @Column(nullable = false)
  @Setter
  private Boolean isAlive;

  @Column(nullable = true)
  @Setter
  private LocalDate dateOfDeath;

  private Pet(
      User owner,
      Long id,
      String name,
      Breed breed,
      LocalDate dateOfBirth,
      Boolean isAlive,
      LocalDate dateOfDeath) {
    this.owner = owner;
    this.id = id;
    this.name = name;
    this.breed = breed;
    this.dateOfBirth = dateOfBirth;
    this.isAlive = isAlive;
    this.dateOfDeath = dateOfDeath;
  }

  public Pet(String name, LocalDate dateOfBirth, Boolean isAlive) {
    this.name = name;
    this.dateOfBirth = dateOfBirth;
    this.isAlive = isAlive;
  }

  public boolean isOwner(User user) {
    return owner.isSameUser(user);
  }

  public boolean isNotOwner(User user) {
    return !owner.isSameUser(user);
  }
}

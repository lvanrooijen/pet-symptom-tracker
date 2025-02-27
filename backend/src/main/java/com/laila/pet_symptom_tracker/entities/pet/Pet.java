package com.laila.pet_symptom_tracker.entities.pet;

import com.laila.pet_symptom_tracker.entities.petbreed.PetBreed;
import com.laila.pet_symptom_tracker.entities.pettype.PetType;
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

  @ManyToOne @Setter private PetType petType;

  @ManyToOne private PetBreed petBreed;

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
      PetType petType,
      PetBreed petBreed,
      LocalDate dateOfBirth,
      Boolean isAlive,
      LocalDate dateOfDeath) {
    this.owner = owner;
    this.id = id;
    this.name = name;
    this.petType = petType;
    this.petBreed = petBreed;
    this.dateOfBirth = dateOfBirth;
    this.isAlive = isAlive;
    this.dateOfDeath = dateOfDeath;
  }

  public Pet(String name, LocalDate dateOfBirth, Boolean isAlive) {
    this.name = name;
    this.dateOfBirth = dateOfBirth;
    this.isAlive = isAlive;
  }

  public Pet(User owner, String name, LocalDate dateOfBirth, Boolean isAlive) {
    this.owner = owner;
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

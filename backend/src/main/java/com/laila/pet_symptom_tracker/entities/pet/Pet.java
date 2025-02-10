package com.laila.pet_symptom_tracker.entities.pet;

import com.laila.pet_symptom_tracker.entities.petbreed.PetBreed;
import com.laila.pet_symptom_tracker.entities.pettype.PetType;
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
  @Setter @ManyToOne User owner;
  @Id @GeneratedValue private Long id;
  @Setter private String name;

  @ManyToOne private PetType petType;

  @ManyToOne private PetBreed petBreed;

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

  public Pet(User owner, String name, LocalDate dateOfBirth, Boolean isAlive) {
    this.owner = owner;
    this.name = name;
    this.dateOfBirth = dateOfBirth;
    this.isAlive = isAlive;
  }
}

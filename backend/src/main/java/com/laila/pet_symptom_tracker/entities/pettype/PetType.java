package com.laila.pet_symptom_tracker.entities.pettype;

import com.laila.pet_symptom_tracker.entities.user.User;
import jakarta.persistence.*;
import lombok.Builder;
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

  @ManyToOne private User createdBy;

  @Builder
  private PetType(String name, User createdBy) {
    this.name = name;
    this.createdBy = createdBy;
  }
}

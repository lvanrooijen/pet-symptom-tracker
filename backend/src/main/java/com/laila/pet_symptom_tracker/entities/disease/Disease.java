package com.laila.pet_symptom_tracker.entities.disease;

import com.laila.pet_symptom_tracker.entities.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
public class Disease {
  @Id @GeneratedValue private Long id;

  @Column(nullable = false)
  @Setter
  private String name;

  @Column(nullable = true)
  @Setter
  private String description;

  @ManyToOne private User createdBy;

  @Builder
  private Disease(String name, String description, User createdBy) {
    this.name = name;
    this.description = description;
    this.createdBy = createdBy;
  }
}

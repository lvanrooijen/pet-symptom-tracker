package com.laila.pet_symptom_tracker.entities.pettype;

import com.laila.pet_symptom_tracker.entities.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;

@Entity
@NoArgsConstructor
@Getter
@SQLDelete(sql = "UPDATE pet_type SET deleted = true WHERE id=?")
public class PetType {
  @Id @GeneratedValue private Long id;

  @Column(nullable = false)
  @Setter
  private String name;

  @Column(nullable = false)
  @Setter
  private boolean deleted = false;

  @ManyToOne private User createdBy;

  @Builder
  private PetType(String name, User createdBy) {
    this.name = name;
    this.createdBy = createdBy;
  }
}

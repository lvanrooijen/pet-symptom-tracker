package com.laila.pet_symptom_tracker.entities.symptom;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

@Entity
@NoArgsConstructor
@Getter
@SQLDelete(sql = "UPDATE symptom SET deleted = true WHERE id=?")
public class Symptom {
  @Id @GeneratedValue private Long id;

  @Column(nullable = false)
  @Setter
  private String name;

  @Column(nullable = false)
  @Setter
  private String description;

  @Column(nullable = false)
  @Setter
  private Boolean deleted = false;

  @Column(nullable = false)
  @Setter
  private Boolean isVerified;

  @Builder
  private Symptom(String name, String description, Boolean isVerified) {
    this.name = name;
    this.description = description;
    this.isVerified = isVerified;
  }
}

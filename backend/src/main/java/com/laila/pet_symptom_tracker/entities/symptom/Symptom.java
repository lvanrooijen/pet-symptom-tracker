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
@Table(name = "symptoms")
public class Symptom {
  @Column(name = "id")
  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false, name = "name")
  @Setter
  private String name;

  @Column(nullable = false, name = "description")
  @Setter
  private String description;

  @Column(nullable = false, name = "is_deleted")
  @Setter
  private Boolean isDeleted = false;

  @Column(nullable = false, name = "is_verified")
  @Setter
  private Boolean isVerified;

  @Builder
  private Symptom(String name, String description, Boolean isVerified) {
    this.name = name;
    this.description = description;
    this.isVerified = isVerified;
  }

  public Symptom(Long id, String name, String description, Boolean isDeleted, Boolean isVerified) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.isDeleted = isDeleted;
    this.isVerified = isVerified;
  }
}

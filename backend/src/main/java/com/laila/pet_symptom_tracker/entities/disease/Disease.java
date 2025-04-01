package com.laila.pet_symptom_tracker.entities.disease;

import com.laila.pet_symptom_tracker.entities.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

@Entity
@NoArgsConstructor
@Getter
@SQLDelete(sql = "UPDATE disease SET deleted = true WHERE id=?")
public class Disease {
  @Id @GeneratedValue private Long id;

  @Column(nullable = false)
  @Setter
  private String name;

  @Column(nullable = true)
  @Setter
  private String description;

  @Column(nullable = false)
  @Setter
  private boolean deleted = false;

  @ManyToOne private User createdBy;

  @Builder
  private Disease(String name, String description, User createdBy) {
    this.name = name;
    this.description = description;
    this.createdBy = createdBy;
  }

  public Disease(Long id, String name, String description, boolean deleted, User createdBy) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.deleted = deleted;
    this.createdBy = createdBy;
  }
}

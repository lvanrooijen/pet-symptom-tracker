package com.laila.pet_symptom_tracker.entities.blacklistedword;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class BlackListedWord {
  @Id @GeneratedValue private Long id;

  @Setter
  @Column(unique = true, nullable = false)
  private String word;

  public BlackListedWord(String word) {
    this.word = word;
  }
}

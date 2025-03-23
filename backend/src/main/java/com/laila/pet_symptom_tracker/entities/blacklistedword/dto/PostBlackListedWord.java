package com.laila.pet_symptom_tracker.entities.blacklistedword.dto;

public record PostBlackListedWord(String word) {
  public PostBlackListedWord(String word) {
    this.word = word.toLowerCase().trim();
  }
}

package com.laila.pet_symptom_tracker.entities.user;

public enum Role {
  ADMIN("ADMIN"),
  MODERATOR("MODERATOR"),
  USER("USER");

  public final String label;

  private Role(String label) {
    this.label = label;
  }
}

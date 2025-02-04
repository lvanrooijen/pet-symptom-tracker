package com.laila.pet_symptom_tracker.exceptions;

public class UsernameNotFoundException extends RuntimeException {
  public UsernameNotFoundException(String message) {
    super(message);
  }

  public UsernameNotFoundException() {}
}

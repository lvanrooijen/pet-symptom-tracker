package com.laila.pet_symptom_tracker.entities.authentication;

import java.util.ArrayList;
import java.util.List;

public enum Authorities {
  // Generic authorities
  READ_ALL_PETS(List.of(Role.ADMIN, Role.MODERATOR));
  // Moderator authorities

  // Admin authorities

  private final List<Role> roles;

  Authorities(List<Role> roles) {
    this.roles = roles;
  }

  public static List<String> getByRole(Role role) {
    List<String> authoritiesForRole = new ArrayList<>();
    for (Authorities authority : Authorities.values()) {
      if (authority.getRoles().contains(role)) {
        authoritiesForRole.add(authority.toString());
      }
    }
    return authoritiesForRole;
  }

  public List<Role> getRoles() {
    return roles;
  }

  @Override
  public String toString() {
    return super.toString();
  }
}

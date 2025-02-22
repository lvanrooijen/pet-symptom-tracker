package com.laila.pet_symptom_tracker.entities.authentication;

import java.util.ArrayList;
import java.util.List;

public enum Authorities {
  // Generic authorities (User-specific)
  READ_OWN_PROFILE(List.of(Role.USER, Role.MODERATOR, Role.ADMIN)),
  WRITE_OWN_PROFILE(List.of(Role.USER, Role.MODERATOR, Role.ADMIN)),
  DELETE_OWN_PROFILE(List.of(Role.USER, Role.MODERATOR, Role.ADMIN)),

  // Moderator authorities
  READ_OTHERS_PROFILE(List.of(Role.MODERATOR, Role.ADMIN)),
  WRITE_OTHERS_PROFILE(List.of(Role.MODERATOR, Role.ADMIN)),

  // Admin authorities
  DELETE_OTHERS_PROFILE(List.of(Role.ADMIN));

  private final List<Role> roles;

  Authorities(List<Role> roles) {
    this.roles = roles;
  }

  public static List<Authorities> getByRole(Role role) {
    List<Authorities> authoritiesForRole = new ArrayList<>();
    for (Authorities authority : Authorities.values()) {
      if (authority.getRoles().contains(role)) {
        authoritiesForRole.add(authority);
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

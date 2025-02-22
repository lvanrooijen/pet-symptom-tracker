package com.laila.pet_symptom_tracker.entities.authentication;

import java.util.ArrayList;
import java.util.List;

public enum Authorities {
  // Ik ben bij hoofdstuk 7, moet betere strategie voor die authorities verzinnen!
  // pas dingen aanmaken als nodig, Yagni!
  // Generic authorities
  READ_ALL_PETS(List.of(Role.ADMIN, Role.MODERATOR));
  // Moderator authorities

  // Admin authorities

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

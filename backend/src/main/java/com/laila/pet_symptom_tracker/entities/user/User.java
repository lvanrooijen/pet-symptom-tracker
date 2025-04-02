package com.laila.pet_symptom_tracker.entities.user;

import com.laila.pet_symptom_tracker.entities.authentication.Role;
import jakarta.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity(name = "users")
@Getter
@NoArgsConstructor
public class User implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  UUID id;

  @Column(unique = true, nullable = false)
  @Setter
  private String email;

  @Column(nullable = false)
  @Setter
  private String password;

  @Column(unique = true, nullable = false)
  @Setter
  private String username;

  @Setter private Role role;

  @Column(nullable = true)
  @Setter
  private String firstName;

  @Column(nullable = true)
  @Setter
  private String lastName;

  @Column(nullable = false)
  @Setter
  private Boolean enabled;

  @Column(nullable = false)
  @Setter
  private Boolean locked;

  public User(String email, String password, String username, Role role) {
    this.email = email;
    this.password = password;
    this.username = username;
    this.enabled = true;
    this.locked = false;
    this.role = role;
  }

  @Builder
  private User(
      UUID id,
      String email,
      String password,
      String username,
      Role role,
      String firstName,
      String lastName,
      Boolean enabled,
      Boolean locked) {
    this.id = id;
    this.email = email;
    this.password = password;
    this.username = username;
    this.role = role;
    this.firstName = firstName;
    this.lastName = lastName;
    this.enabled = enabled;
    this.locked = locked;
  }

  public boolean hasRole(Role role) {
    return this.role.equals(role);
  }

  public Boolean isLocked() {
    return this.locked;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.toString()));
  }

  @Override
  public boolean isAccountNonLocked() {
    return locked;
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }

  /*  ~~~~~~~~~~~  Helper methods ~~~~~~~~~~~  */
  public Boolean hasAdminRole() {
    return hasRole(Role.ADMIN);
  }

  public Boolean hasModeratorRole() {
    return hasRole(Role.MODERATOR);
  }

  public Boolean isSameUser(Object otherUser) {
    if (otherUser instanceof User) {
      return this.getId().equals(((User) otherUser).id);
    }
    return false;
  }

  public Boolean hasUserRole() {
    return hasRole(Role.USER);
  }
}

package com.laila.pet_symptom_tracker.entities.user;

import jakarta.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
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

  public static final String ROLE_PREFIX = "ROLE_";

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

  private String role;

  @Column(nullable = true)
  @Setter
  private String firstName;

  @Column(nullable = true)
  @Setter
  private String lastName;

  @Column(nullable = false)
  @Setter
  private Boolean enabled;

  public User(String email, String password, String username, Role role) {
    this.email = email;
    this.password = password;
    this.username = username;
    this.enabled = true;
    setRole(role);
  }

  public boolean hasRole(Role role) {
    return this.role.equals(ROLE_PREFIX + role.label);
  }

  public void setRole(Role role) {
    this.role = ROLE_PREFIX + role.label;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role));
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof User otherUser)) return false;
    return id.equals(otherUser.id);
  }

  @Override
  public boolean isAccountNonExpired() {
    return UserDetails.super.isAccountNonExpired();
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return this.enabled;
  }
}

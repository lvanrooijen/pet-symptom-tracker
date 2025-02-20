package com.laila.pet_symptom_tracker.entities.user;

import com.laila.pet_symptom_tracker.entities.authentication.Role;
import jakarta.persistence.*;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "users")
@Getter
@NoArgsConstructor
public class User {
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

  @Column(nullable = false)
  @Setter
  private Boolean locked;

  public User(String email, String password, String username, Role role) {
    this.email = email;
    this.password = password;
    this.username = username;
    this.enabled = true;
    this.locked = false;
    this.role = role.toString();
  }

  public boolean hasRole(Role role) {
    return this.role.equals(role.toString());
  }

  public void setRole(Role role) {
    // TODO voor later, alleen een admin mag een role aanpassen van user naar mod of mod naar user
    this.role = role.toString();
  }

  public Boolean isAdmin() {
    return hasRole(Role.ADMIN);
  }

  public Boolean isModerator() {
    return hasRole(Role.MODERATOR);
  }

  public Boolean isUser() {
    return hasRole(Role.USER);
  }

  public Boolean isLocked() {
    return this.locked;
  }
}

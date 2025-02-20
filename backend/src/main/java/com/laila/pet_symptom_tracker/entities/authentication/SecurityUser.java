package com.laila.pet_symptom_tracker.entities.authentication;

import com.laila.pet_symptom_tracker.entities.user.User;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUser implements UserDetails {
  private final User user;

  public SecurityUser(User user) {
    this.user = user;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(user.getRole()));
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getUsername();
  }

  @Override
  public boolean isAccountNonLocked() {
    return user.getLocked();
  }

  @Override
  public boolean isEnabled() {
    return user.getEnabled();
  }

  public boolean isSameUser(Object other) {
    if (other instanceof SecurityUser otherSecurityUser) {
      return this.user.getId().equals(otherSecurityUser.user.getId());
    } else if (other instanceof User otherUser) {
      return this.user.getId().equals(otherUser.getId());
    }
    return false;
  }
}

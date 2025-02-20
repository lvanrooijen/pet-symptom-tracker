package com.laila.pet_symptom_tracker.entities.authentication;

import com.laila.pet_symptom_tracker.entities.user.User;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;

@RequiredArgsConstructor
public class SecurityUser implements UserDetailsManager, UserDetails {
  private final User user;

  @Override
  public void createUser(UserDetails user) {}

  @Override
  public void updateUser(UserDetails user) {}

  @Override
  public void deleteUser(String username) {}

  @Override
  public void changePassword(String oldPassword, String newPassword) {}

  @Override
  public boolean userExists(String username) {
    return false;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return null;
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

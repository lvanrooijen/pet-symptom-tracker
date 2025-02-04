package com.laila.pet_symptom_tracker.entities.user;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {
  Optional<User> findByUsernameIgnoreCase(String username);

  Optional<User> findByEmailIgnoreCase(String email);
}

package com.laila.pet_symptom_tracker;

import com.laila.pet_symptom_tracker.entities.user.User;
import com.laila.pet_symptom_tracker.entities.user.UserRepository;
import com.laila.pet_symptom_tracker.entities.user.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Seeder implements CommandLineRunner {
  private final UserRepository userRepository;

  @Override
  public void run(String... args) throws Exception {
    seedUsers();
  }

  private void seedUsers() {
    userRepository.save(new User("Charlie@gmail.com", "Password123!", "Charlie", Role.USER));
    userRepository.save(new User("Carol@gmail.com", "Password123!", "Carol", Role.USER));
    userRepository.save(new User("admin@gmail.com", "Password123!", "Admin", Role.ADMIN));
    userRepository.save(
        new User("moderator@gmail.com", "Password123!", "Moderator", Role.MODERATOR));
    userRepository.save(new User("user@gmail.com", "Password123!", "user", Role.USER));
  }
}

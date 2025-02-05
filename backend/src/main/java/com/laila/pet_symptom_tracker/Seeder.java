package com.laila.pet_symptom_tracker;

import com.laila.pet_symptom_tracker.entities.user.UserRepository;
import com.laila.pet_symptom_tracker.mainconfig.MockData;
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
    MockData.getUsers().forEach(userRepository::save);
  }
}

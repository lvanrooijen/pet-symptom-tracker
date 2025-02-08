package com.laila.pet_symptom_tracker;

import com.laila.pet_symptom_tracker.entities.pets.PetRepository;
import com.laila.pet_symptom_tracker.entities.user.User;
import com.laila.pet_symptom_tracker.entities.user.UserRepository;
import com.laila.pet_symptom_tracker.mainconfig.MockData;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Seeder implements CommandLineRunner {
  private final UserRepository userRepository;
  private final PetRepository petRepository;

  @Override
  public void run(String... args) throws Exception {
    seedUsers();
    seedPets();
  }

  private void seedUsers() {
    MockData.getUsers().forEach(userRepository::save);
  }

  private void seedPets() {
    User charlie = userRepository.findByUsernameIgnoreCase("charlie").orElse(null);

    MockData.getPets()
        .forEach(
            pet -> {
              pet.setUser(charlie);
              petRepository.save(pet);
            });
  }
}

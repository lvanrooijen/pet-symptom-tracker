package com.laila.pet_symptom_tracker;

import com.laila.pet_symptom_tracker.entities.pet.PetRepository;
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
    // todo seed stuff!
  }
}

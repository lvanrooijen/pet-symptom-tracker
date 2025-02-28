package com.laila.pet_symptom_tracker;

import com.laila.pet_symptom_tracker.entities.authentication.Role;
import com.laila.pet_symptom_tracker.entities.breed.Breed;
import com.laila.pet_symptom_tracker.entities.breed.BreedRepository;
import com.laila.pet_symptom_tracker.entities.pet.Pet;
import com.laila.pet_symptom_tracker.entities.pet.PetRepository;
import com.laila.pet_symptom_tracker.entities.pettype.PetType;
import com.laila.pet_symptom_tracker.entities.pettype.PetTypeRepository;
import com.laila.pet_symptom_tracker.entities.user.User;
import com.laila.pet_symptom_tracker.entities.user.UserRepository;
import com.laila.pet_symptom_tracker.entities.user.UserService;
import com.laila.pet_symptom_tracker.mainconfig.MockData;
import java.util.List;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Seeder implements CommandLineRunner {
  private final Random random = new Random();
  private final PasswordEncoder passwordEncoder;
  private final UserService userService;
  private final UserRepository userRepository;
  private final PetRepository petRepository;
  private final PetTypeRepository petTypeRepository;
  private final BreedRepository breedRepository;

  @Override
  public void run(String... args) throws Exception {
    seedPetTypes();
    seedBreeds();
    seedUsers();
    seedPets();
  }

  private void seedBreeds() {
    if (!breedRepository.findAll().isEmpty()) return;
    List<PetType> types = petTypeRepository.findAll();
    if (types.isEmpty()) return;

    MockData.getBreeds()
        .forEach(
            breed ->
                breedRepository.save(
                    new Breed(
                        breed.name(), petTypeRepository.findById(breed.petTypeId()).orElse(null))));
  }

  private void seedPetTypes() {
    if (!petTypeRepository.findAll().isEmpty()) return;
    petTypeRepository.saveAll(MockData.getPetTypes());
  }

  private void seedUsers() {
    List<User> users = MockData.getUsers();
    users.forEach(user -> user.setPassword(passwordEncoder.encode("Password123!")));
    users.forEach(userService::createUser);
  }

  private void seedPets() {
    if (!petRepository.findAll().isEmpty()) return;
    List<User> users = userRepository.findByRole(Role.USER);
    if (users.isEmpty()) return;
    List<Breed> breeds = breedRepository.findAll();

    List<Pet> pets = MockData.getPets();

    int maxBreed = breeds.size() - 1;
    int maxUsers = users.size() - 1;

    for (Pet pet : pets) {
      pet.setOwner(users.get(random.nextInt(0, maxUsers)));
      pet.setBreed(breeds.get(random.nextInt(0, maxBreed)));
    }

    petRepository.saveAll(pets);
  }
}

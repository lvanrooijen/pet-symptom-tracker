package com.laila.pet_symptom_tracker.mainconfig;

import com.laila.pet_symptom_tracker.entities.user.User;
import com.laila.pet_symptom_tracker.entities.user.enums.Role;
import java.util.List;

public class MockData {
  public static List<User> getUsers() {
    return List.of(
        new User("charlie@gmail.com", "Password123!", "Charlie", Role.USER),
        new User("carol@gmail.com", "Password123!", "Carol", Role.USER),
        new User("dave@gmail.com", "Password123!", "Dave", Role.USER),
        new User("bob@gmail.com", "Password123!", "Bob", Role.USER),
        new User("luna@gmail.com", "SecurePass456!", "Luna", Role.USER),
        new User("milo@gmail.com", "MiloRocks789!", "Milo", Role.USER),
        new User("sophie@gmail.com", "SophiePass!", "Sophie", Role.USER),
        new User("max@gmail.com", "MaxRules999!", "Max", Role.USER),
        new User("olivia@gmail.com", "OliviaSecret!", "Olivia", Role.USER),
        new User("admin@gmail.com", "Password123!", "Admin", Role.ADMIN),
        new User("moderator@gmail.com", "Password123!", "Moderator", Role.MODERATOR),
        new User("user@gmail.com", "Password123!", "user", Role.USER));
  }
}

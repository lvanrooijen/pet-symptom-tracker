package com.laila.pet_symptom_tracker.entities.user.dto;

import static org.junit.jupiter.api.Assertions.*;

import com.laila.pet_symptom_tracker.entities.user.User;
import com.laila.pet_symptom_tracker.entities.user.enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserAuthDtoTest {
  private User user = null;

  @BeforeEach
  void setUp() {
    user = new User("jackie@gmail.com", "Password123!", "wacky_jacky", Role.USER);
  }

  @Test
  void should_create_dto_from_user() {
    UserAuthDto dto = UserAuthDto.from(user);
    assertEquals(user.getId(), dto.id());
    assertEquals(user.getEmail(), dto.email());
    assertEquals(user.getRole(), dto.role());
    assertEquals(user.getEnabled(), dto.enabled());
  }
}

package com.laila.pet_symptom_tracker.entities.user;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class UserServiceTest {
  @Mock private UserRepository userRepository;
  @InjectMocks private UserService userService;
}

package com.laila.pet_symptom_tracker.entities.user;

import static org.junit.jupiter.api.Assertions.*;

import com.laila.pet_symptom_tracker.entities.user.dto.RegisterDto;
import com.laila.pet_symptom_tracker.entities.user.enums.Role;
import com.laila.pet_symptom_tracker.securityconfig.JwtService;
import java.lang.reflect.Field;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

class UserServiceTest {
  User user = null;
  @Mock private UserRepository userRepository;
  @Mock private PasswordEncoder passwordEncoder;
  @Mock private JwtService jwtService;
  @InjectMocks private UserService userService;

  @BeforeEach
  void setUp() throws NoSuchFieldException, IllegalAccessException {
    MockitoAnnotations.openMocks(this);
    user = new User("iniko@gmail.com", "Password123!", "ini", Role.USER);
    // I don't want to create a setter for id or custom constructor just for testing purposes in the
    // user class therefor i'm using reflection
    Field idField = User.class.getDeclaredField("id");
    idField.setAccessible(true);
    UUID id = UUID.randomUUID();
    idField.set(user, id);
    idField.setAccessible(false);
  }

  @Test
  void register() {
    // given
    RegisterDto dto = new RegisterDto("ini", "iniko@gmail.com", "Password123!", null, null);
    // mock all calls
    Mockito.when(userRepository.findByUsernameIgnoreCase(user.getUsername()))
        .thenReturn(Optional.empty());
    Mockito.when(userRepository.findByEmailIgnoreCase(user.getEmail()))
        .thenReturn(Optional.empty());
    Mockito.when(passwordEncoder.encode(user.getPassword())).thenReturn(user.getPassword());
    Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
    // when
    User created = userService.register(dto);
    // then
    assertEquals(user.getUsername(), created.getUsername());
    assertEquals(user.getEmail(), created.getEmail());
    assertEquals(user.getPassword(), created.getPassword());
    assertEquals(user.getRole(), created.getRole());
    Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any(User.class));
  }
}

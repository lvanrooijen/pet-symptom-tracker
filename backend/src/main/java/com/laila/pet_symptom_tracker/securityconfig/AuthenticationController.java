package com.laila.pet_symptom_tracker.securityconfig;

import com.laila.pet_symptom_tracker.entities.user.User;
import com.laila.pet_symptom_tracker.entities.user.UserRepository;
import com.laila.pet_symptom_tracker.entities.user.UserService;
import com.laila.pet_symptom_tracker.entities.user.dto.LoginDto;
import com.laila.pet_symptom_tracker.entities.user.dto.RegisterDto;
import com.laila.pet_symptom_tracker.entities.user.dto.UserAuthDto;
import com.laila.pet_symptom_tracker.exceptions.BadRequestException;
import com.laila.pet_symptom_tracker.mainconfig.Routes;
import com.laila.pet_symptom_tracker.securityconfig.dto.TokenDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Routes.AUTHENTICATION)
@RequiredArgsConstructor
@CrossOrigin(origins = "${pet_symptom_tracker.cors}")
public class AuthenticationController {
  private final JwtService jwtService;
  private final UserService userService;
  private final UserRepository userRepository;

  @PostMapping("/login")
  public ResponseEntity<TokenDto> login(@RequestBody @Valid LoginDto loginDto) {
    User user =
        userRepository
            .findByUsernameIgnoreCase(loginDto.username())
            .orElseThrow(() -> new BadRequestException("user does not exist"));

    if (!user.isEnabled()) throw new BadRequestException("account is disabled");

    return ResponseEntity.ok(
        new TokenDto(jwtService.generateTokenForUser(user), UserAuthDto.from(user)));
  }

  @PostMapping("/register")
  public ResponseEntity<TokenDto> register(@RequestBody @Valid RegisterDto registerDto) {
    User user = userService.register(registerDto);

    return ResponseEntity.ok(
        new TokenDto(jwtService.generateTokenForUser(user), UserAuthDto.from(user)));
  }
}

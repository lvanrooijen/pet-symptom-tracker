package com.laila.pet_symptom_tracker.entities.authentication;

import com.laila.pet_symptom_tracker.entities.authentication.dto.LoginRequest;
import com.laila.pet_symptom_tracker.entities.authentication.dto.LoginResponse;
import com.laila.pet_symptom_tracker.entities.authentication.dto.RegisterRequest;
import com.laila.pet_symptom_tracker.entities.user.User;
import com.laila.pet_symptom_tracker.entities.user.UserService;
import com.laila.pet_symptom_tracker.entities.user.dto.GetUser;
import com.laila.pet_symptom_tracker.mainconfig.ColoredLogger;
import com.laila.pet_symptom_tracker.mainconfig.Routes;
import com.laila.pet_symptom_tracker.securityconfig.JwtService;
import com.laila.pet_symptom_tracker.securityconfig.JwtToken;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.logging.Logger;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping(Routes.BASE_ROUTE)
public class AuthenticationController {
  private final Logger log = Logger.getLogger(AuthenticationController.class.getName());
  private final UserService userService;
  private final JwtService jwtService;

  // user registreren
  @PostMapping("/register")
  public ResponseEntity<LoginResponse> register(
      @RequestBody @Valid RegisterRequest registerRequest) {
    ColoredLogger.prettyInPink("Register attempt");
    ColoredLogger.prettyInPink("Register request: " + registerRequest);
    User createdUser = userService.register(registerRequest);
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("{/id}")
            .buildAndExpand(createdUser.getId())
            .toUri();

    String token = jwtService.generateTokenForUser(createdUser);

    LoginResponse response = new LoginResponse(token, GetUser.from(createdUser));

    return ResponseEntity.created(location).body(response);
  }

  @PostMapping("/login")
  public ResponseEntity<JwtToken> login(@RequestBody LoginRequest loginRequest) {
    ColoredLogger.logInBlue("login attempt");

    User user = userService.login(loginRequest);

    JwtToken token = new JwtToken(jwtService.generateTokenForUser(user));

    return ResponseEntity.ok(token);
  }

  // create account als MODERATOR

  // create account als ADMIN

  // user updaten

  // user inloggen

  // user ww veranderen

  // user eigen account verwijderen
}

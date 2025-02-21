package com.laila.pet_symptom_tracker.entities.authentication;

import com.laila.pet_symptom_tracker.entities.authentication.dto.RegisterUser;
import com.laila.pet_symptom_tracker.entities.user.UserService;
import com.laila.pet_symptom_tracker.entities.user.dto.GetUser;
import com.laila.pet_symptom_tracker.mainconfig.Routes;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(Routes.AUTHENTICATION)
@RequiredArgsConstructor
@CrossOrigin(origins = "${pet_symptom_tracker.cors}")
public class AuthenticationController {
  private final UserService userService;

  // user registeren
  @PostMapping("/register")
  public ResponseEntity<GetUser> register(@RequestBody @Valid RegisterUser registerUser) {
    GetUser createdUser = userService.register(registerUser);
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("{/id}")
            .buildAndExpand(createdUser.id())
            .toUri();
    return ResponseEntity.created(location).body(createdUser);
  }

  // user updaten

  // user inloggen

  // user ww veranderen

  // user eigen account verwijderen
}

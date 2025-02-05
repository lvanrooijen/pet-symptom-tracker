package com.laila.pet_symptom_tracker.entities.user;

import com.laila.pet_symptom_tracker.entities.user.dto.GetUser;
import com.laila.pet_symptom_tracker.entities.user.dto.PatchUser;
import com.laila.pet_symptom_tracker.entities.user.enums.UserControllerActions;
import com.laila.pet_symptom_tracker.mainconfig.Routes;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Routes.USERS)
@RequiredArgsConstructor
@CrossOrigin(origins = "${pet_symptom_tracker.cors}")
public class UserController {
  private final UserService userService;

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/{id}")
  public ResponseEntity<GetUser> getById(@PathVariable UUID id, Authentication authentication) {

    User loggedInUser = (User) authentication.getPrincipal();

    return ResponseEntity.ok(userService.getById(id, loggedInUser));
  }

  @PreAuthorize("hasAnyRole('ADMIN','MODERATOR')")
  @GetMapping
  public ResponseEntity<List<GetUser>> getAll(Authentication authentication) {
    User loggedInUser = (User) authentication.getPrincipal();

    return ResponseEntity.ok(userService.GetAll(loggedInUser));
  }

  @PreAuthorize("isAuthenticated()")
  @DeleteMapping("/{id}")
  public ResponseEntity<GetUser> deleteUser(@PathVariable UUID id, Authentication authentication) {
    User loggedInUser = (User) authentication.getPrincipal();

    userService.deleteById(id, loggedInUser);

    return ResponseEntity.ok().build();
  }

  @PreAuthorize("hasAnyRole('ADMIN','MODERATOR')")
  @PostMapping("/{id}")
  public ResponseEntity<GetUser> banUnbanUser(
      @PathVariable UUID id,
      Authentication authentication,
      @RequestParam(required = true) UserControllerActions action) {
    User loggedInUser = (User) authentication.getPrincipal();

    return ResponseEntity.ok().body(userService.banUnbanUser(id, loggedInUser, action));
  }

  @PreAuthorize("isAuthenticated()")
  @PatchMapping("/{id}")
  public ResponseEntity<GetUser> patchUser(
      @PathVariable UUID id, Authentication authentication, @RequestBody PatchUser patch) {
    User loggedInUser = (User) authentication.getPrincipal();

    return ResponseEntity.ok(userService.update(id, loggedInUser, patch));
  }
}

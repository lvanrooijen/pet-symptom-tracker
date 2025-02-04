package com.laila.pet_symptom_tracker.entities.hello;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

  @GetMapping("/hello")
  public ResponseEntity<String> returnHello() {
    return ResponseEntity.ok("Hello");
  }
}

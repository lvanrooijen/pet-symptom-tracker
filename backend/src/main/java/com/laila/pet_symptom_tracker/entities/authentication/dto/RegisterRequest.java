package com.laila.pet_symptom_tracker.entities.authentication.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
    @NotBlank String username,
    @Email @NotBlank String email,
    @NotBlank String password,
    String firstname,
    String lastname) {}

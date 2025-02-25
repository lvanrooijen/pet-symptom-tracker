package com.laila.pet_symptom_tracker.entities.authentication.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterRequest(
    @NotNull @NotBlank String username,
    @NotNull @Email @NotBlank String email,
    @NotNull @NotBlank String password,
    String firstname,
    String lastname) {}

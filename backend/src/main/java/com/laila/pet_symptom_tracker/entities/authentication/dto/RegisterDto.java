package com.laila.pet_symptom_tracker.entities.authentication.dto;

import jakarta.validation.constraints.NotBlank;

public record RegisterDto(
    @NotBlank String username,
    @NotBlank String email,
    @NotBlank String password,
    String firstname,
    String lastname) {}

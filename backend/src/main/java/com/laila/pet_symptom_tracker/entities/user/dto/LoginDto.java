package com.laila.pet_symptom_tracker.entities.user.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginDto(@NotBlank String username, @NotBlank String password) {}

package com.laila.pet_symptom_tracker.entities.breed.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.NumberFormat;

public record PostBreed(
    @NotBlank String name,
    @NotNull @NumberFormat(style = NumberFormat.Style.NUMBER) Long petTypeId) {}

package com.laila.pet_symptom_tracker.securityconfig.dto;

import com.laila.pet_symptom_tracker.entities.user.dto.UserAuthDto;

public record TokenDto(String token, UserAuthDto user) {}

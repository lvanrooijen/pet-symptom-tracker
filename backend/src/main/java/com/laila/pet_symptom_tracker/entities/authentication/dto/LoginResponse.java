package com.laila.pet_symptom_tracker.entities.authentication.dto;

import com.laila.pet_symptom_tracker.entities.user.dto.GetUser;

public record LoginResponse(String token, GetUser user) {}

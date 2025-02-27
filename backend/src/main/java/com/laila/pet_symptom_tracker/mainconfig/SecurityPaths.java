package com.laila.pet_symptom_tracker.mainconfig;

public class SecurityPaths {
  public static final String[] OPEN_GET_PATHS = {"/api/v1/pet-types", "/api/v1/pet-types/**"};
  public static final String[] OPEN_POST_PATHS = {"/api/v1/login", "/api/v1/register"};
  public static final String[] ADMIN_MODERATOR_PATHS = {
    "/api/v1/pet-types", "/api/v1/pet-types/**"
  };

  // public static String[] ADMIN_ONLY_PATHS = {};
}

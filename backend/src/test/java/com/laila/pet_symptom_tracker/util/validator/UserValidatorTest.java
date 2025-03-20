package com.laila.pet_symptom_tracker.util.validator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserValidatorTest {

  @Test
  @DisplayName("Returns true for emails with a valid pattern.")
  void isEmailValid() {
    String validEmail = "Human@outlook.com";
    String validEmail1 = "Human@proton.me";

    assertTrue(UserValidator.isValidEmailPattern(validEmail));
    assertTrue(UserValidator.isValidEmailPattern(validEmail1));
  }

  @Test
  @DisplayName("Returns false for emails with an invalid pattern.")
  void isEmailInvalid() {
    String invalidEmail = "Splash!";
    String invalidEmail1 = "Splash@!.com";

    assertFalse(UserValidator.isValidEmailPattern(invalidEmail));
    assertFalse(UserValidator.isValidEmailPattern(invalidEmail1));
  }

  @Disabled("Disabled because spring boot validation is used for this at the moment.")
  @Test
  @DisplayName("Returns true if password meets requirements.")
  void isValidPassword() {
    String validPassword = "abcDEF!123";
    String validPassword1 = "?@1234abcDEF";
    assertTrue(UserValidator.isValidPasswordPattern(validPassword));
    assertTrue(UserValidator.isValidPasswordPattern(validPassword1));
  }

  @Disabled("Disabled because spring boot validation is used for this at the moment.")
  @Test
  @DisplayName("Returns false if password does not meet requirements.")
  void isInvalidPassword() {
    String invalidPassword = "a";
    String invalidPassword1 = "?";
    assertFalse(UserValidator.isValidPasswordPattern(invalidPassword));
    assertFalse(UserValidator.isValidPasswordPattern(invalidPassword1));
  }

  @Disabled("Disabled because spring boot validation is used for this at the moment.")
  @Test
  @DisplayName("Returns true if username meets requirements.")
  void isValidUsername() {
    String validPassword = "Charlie";
    String validUsername1 = "?@1234abcDEF";
    assertTrue(UserValidator.isValidUsername(validPassword));
    assertTrue(UserValidator.isValidUsername(validUsername1));
  }

  @Disabled("Disabled because spring boot validation is used for this at the moment.")
  @Test
  @DisplayName("Returns false if username does not meet requirements.")
  void isInvalidUsername() {
    String invalidUsername = "";
    String invalidUsername1 = "a";
    String invalidUsername2 = "CharlieChapplingBustAGrooveMaybeAMoveDontKnowWho";
    assertFalse(UserValidator.isValidUsername(invalidUsername));
    assertFalse(UserValidator.isValidUsername(invalidUsername1));
    assertFalse(UserValidator.isValidUsername(invalidUsername2));
  }

  @Test
  @DisplayName("Returns a String that describes the Password requirements")
  void getPasswordRequirements() {
    String passwordRequirements =
        "A password must contain a minimum of 8 characters and a maximum of 16 characters, at least 1 number, 1 uppercase letter and 1 special character";
    assertEquals(passwordRequirements, UserValidator.getPasswordRequirements());
  }
}

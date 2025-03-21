package com.laila.pet_symptom_tracker.util.validator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class UserValidatorTest {

  @DisplayName("Returns true for emails with a valid pattern.")
  @ParameterizedTest
  @ValueSource(
      strings = {
        "Human@outlook.com",
        "Human@proton.me",
        "Human@domain.co.uk",
        "Human@sub.domain.com",
        "Human+alias@email.com"
      })
  void isEmailValid(String email) {
    assertTrue(UserValidator.isValidEmailPattern(email));
  }

  @Test
  @DisplayName("Returns false for emails with an invalid pattern.")
  void isEmailInvalid() {
    String invalidEmail = "Splash!";
    String invalidEmail1 = "Splash@!.com";
    String emailMultipleDots = "Splash@Gmail..com";

    assertFalse(
        UserValidator.isValidEmailPattern(invalidEmail),
        () -> "Email address that doesn't not contain @ should return false");
    assertFalse(
        UserValidator.isValidEmailPattern(invalidEmail1),
        () -> "Email address that contains characters after @ and before . should return false");
    assertFalse(
        UserValidator.isValidEmailPattern(invalidEmail1),
        () -> "Email address with multiple dots should return false");
  }

  @Disabled("Disabled because spring boot validation is used for this at the moment.")
  @ParameterizedTest
  @ValueSource(strings = {"abcDEF!123", "?@1234abcDEF"})
  @DisplayName("Returns true if password meets requirements.")
  void isValidPassword(String password) {
    assertTrue(UserValidator.isValidPasswordPattern(password));
  }

  @Disabled("Disabled because spring boot validation is used for this at the moment.")
  @Test
  @DisplayName("Returns false if password does not meet requirements.")
  void isInvalidPassword() {
    String passwordTooShort = "a";
    String passwordTooLong = "1234567891011121315161718";
    String passwordWithoutNumber = "abcD_E_F_G";
    String passwordWithoutSpecialCharacter = "Abcdef123456";
    String passwordWithoutUppercase = "abcdef123456!";

    assertFalse(
        UserValidator.isValidPasswordPattern(passwordTooShort),
        () -> "Password that does not have minimum of 8 characters should return false.");
    assertFalse(
        UserValidator.isValidPasswordPattern(passwordTooLong),
        () -> "Password that has more then 16 characters should return false.");
    assertFalse(
        UserValidator.isValidPasswordPattern(passwordWithoutNumber),
        () -> "Password that does not contain at least 1 number should return false.");
    assertFalse(
        UserValidator.isValidPasswordPattern(passwordWithoutSpecialCharacter),
        () -> "Password that does not contain a special character should return false.");
    assertFalse(
        UserValidator.isValidPasswordPattern(passwordWithoutUppercase),
        () -> "Password that does not contain at least 1 uppercase letter should return false.");
  }

  @Disabled("Disabled because spring boot validation is used for this at the moment.")
  @ParameterizedTest
  @ValueSource(strings = {"Charlie", "?@1234abcDEF"})
  @DisplayName("Returns true if username meets requirements.")
  void isValidUsername(String username) {
    assertTrue(UserValidator.isValidUsername(username));
  }

  @Disabled("Disabled because spring boot validation is used for this at the moment.")
  @Test
  @DisplayName("Returns false if username does not meet requirements.")
  void isInvalidUsername() {
    String blankUsername = "";
    String usernameTooShort = "a";
    String usernameTooLong = "CharlieChapplingBustAGrooveMaybeAMoveDontKnowWho";

    assertFalse(
        UserValidator.isValidUsername(blankUsername), () -> "Blank username should return false.");
    assertFalse(
        UserValidator.isValidUsername(usernameTooShort),
        () -> "Username with less then 3 characters should return false.");
    assertFalse(
        UserValidator.isValidUsername(usernameTooLong),
        () -> "Username with more then 16 characters should return false.");
  }

  @Test
  @DisplayName("Returns a String that describes the Password requirements.")
  void getPasswordRequirements() {
    String passwordRequirements =
        "A password must contain a minimum of 8 characters and a maximum of 16 characters, at least 1 number, 1 uppercase letter and 1 special character.";
    assertEquals(passwordRequirements, UserValidator.getPasswordRequirements());
  }
}

package com.laila.pet_symptom_tracker.entities.blacklistedword;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.laila.pet_symptom_tracker.entities.authentication.AuthenticationService;
import com.laila.pet_symptom_tracker.entities.authentication.Role;
import com.laila.pet_symptom_tracker.entities.blacklistedword.dto.PostBlackListedWord;
import com.laila.pet_symptom_tracker.entities.user.User;
import com.laila.pet_symptom_tracker.exceptions.generic.DuplicateValueException;
import com.laila.pet_symptom_tracker.exceptions.generic.ForbiddenException;
import com.laila.pet_symptom_tracker.exceptions.generic.NotFoundException;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BlackListedWordServiceTest {
  @InjectMocks BlackListedWordService blackListedWordService;
  PostBlackListedWord postBlackListedWord;
  User user;
  User moderator;
  User admin;
  UUID userId;
  BlackListedWord word;
  Long invalidWordId;
  @Mock private BlackListedWordRepository blackListedWordRepository;
  @Mock private AuthenticationService authenticationService;

  @BeforeEach
  public void init() {
    invalidWordId = 999L;
    userId = UUID.randomUUID();
    postBlackListedWord = new PostBlackListedWord("Fuck");
    word = new BlackListedWord("fuck");
    user = new User("user@email.com", "Password123!", "user", Role.USER);
    moderator = new User("mod@email.com", "Password123!", "moderator", Role.MODERATOR);
    admin = new User("admin@gmail.com", "Password123!", "admin", Role.ADMIN);
  }

  @Test
  public void user_created_blacklisted_word_throws_forbidden_exception_with_right_message() {
    when(authenticationService.getAuthenticatedUser()).thenReturn(user);
    ForbiddenException exception =
        assertThrows(
            ForbiddenException.class, () -> blackListedWordService.create(postBlackListedWord));
    assertEquals("Only an admin is allowed to perform this action.", exception.getMessage());
  }

  @Test
  public void moderator_created_blacklisted_word_throws_forbidden_exception_with_right_message() {
    when(authenticationService.getAuthenticatedUser()).thenReturn(moderator);
    ForbiddenException exception =
        assertThrows(
            ForbiddenException.class, () -> blackListedWordService.create(postBlackListedWord));
    assertEquals("Only an admin is allowed to perform this action.", exception.getMessage());
  }

  @Test
  public void admin_created_blacklisted_word_returns_created_word() {
    when(authenticationService.getAuthenticatedUser()).thenReturn(admin);
    when(blackListedWordRepository.findByWordIgnoreCase(word.getWord()))
        .thenReturn(Optional.empty());

    BlackListedWord createdWord = blackListedWordService.create(postBlackListedWord);
    assertEquals(word.getWord(), createdWord.getWord());
  }

  @Test
  public void creating_duplicate_word_throws_duplicate_value_exception_with_correct_message() {
    when(authenticationService.getAuthenticatedUser()).thenReturn(admin);
    when(blackListedWordRepository.findByWordIgnoreCase(word.getWord()))
        .thenReturn(Optional.of(word));

    DuplicateValueException exception =
        assertThrows(
            DuplicateValueException.class,
            () -> blackListedWordService.create(postBlackListedWord));

    assertEquals("This word is already on the blacklist.", exception.getMessage());
  }

  @Test
  public void get_non_existing_blacklisted_word_by_id_throws_not_found_exception() {
    when(blackListedWordRepository.findById(invalidWordId)).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> blackListedWordService.getById(invalidWordId));
  }

  @Test
  public void update_non_blacklisted_word_with_invalid_id_throws_not_found_exception() {
    when(blackListedWordRepository.findById(invalidWordId)).thenReturn(Optional.empty());

    assertThrows(
        NotFoundException.class,
        () -> blackListedWordService.update(invalidWordId, postBlackListedWord));
  }

  @Test
  public void update_blacklisted_word_works() {
    when(blackListedWordRepository.findById(invalidWordId)).thenReturn(Optional.of(word));

    BlackListedWord word =
        blackListedWordService.update(invalidWordId, new PostBlackListedWord("Quack"));

    assertEquals("quack", word.getWord());
  }

  @Test
  public void delete_with_invalid_id_throws_not_found_exception() {
    when(blackListedWordRepository.findById(invalidWordId)).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> blackListedWordService.delete(invalidWordId));
  }
}

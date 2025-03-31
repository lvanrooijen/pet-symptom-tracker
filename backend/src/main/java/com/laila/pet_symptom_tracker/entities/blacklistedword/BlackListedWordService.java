package com.laila.pet_symptom_tracker.entities.blacklistedword;

import com.laila.pet_symptom_tracker.entities.authentication.AuthenticationService;
import com.laila.pet_symptom_tracker.entities.blacklistedword.dto.PostBlackListedWord;
import com.laila.pet_symptom_tracker.entities.user.User;
import com.laila.pet_symptom_tracker.exceptions.generic.DuplicateValueException;
import com.laila.pet_symptom_tracker.exceptions.generic.ForbiddenException;
import com.laila.pet_symptom_tracker.exceptions.generic.NotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlackListedWordService {
  private final BlackListedWordRepository blackListedWordRepository;
  private final AuthenticationService authenticationService;

  public BlackListedWord create(PostBlackListedWord requestBody) {
    User loggedInUser = authenticationService.getAuthenticatedUser();
    if (!loggedInUser.hasAdminRole()) {
      throw new ForbiddenException("Only an admin is allowed to perform this action.");
    }

    BlackListedWord createdWord = new BlackListedWord(requestBody.word());

    if (blackListedWordRepository.findByWordIgnoreCase(requestBody.word()).isPresent()) {
      throw new DuplicateValueException("This word is already on the blacklist.");
    }

    blackListedWordRepository.save(createdWord);
    return createdWord;
  }

  public List<BlackListedWord> getAll() {
    return blackListedWordRepository.findAll();
  }

  public BlackListedWord getById(Long id) {
    return blackListedWordRepository.findById(id).orElseThrow(NotFoundException::new);
  }

  public BlackListedWord update(Long id, PostBlackListedWord patch) {
    BlackListedWord word =
        blackListedWordRepository.findById(id).orElseThrow(NotFoundException::new);

    if (patch.word() != null) {
      word.setWord(patch.word());
    }

    blackListedWordRepository.save(word);

    return word;
  }

  public void delete(Long id) {
    blackListedWordRepository.findById(id).orElseThrow(NotFoundException::new);
    blackListedWordRepository.deleteById(id);
  }
}

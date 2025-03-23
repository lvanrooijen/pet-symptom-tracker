package com.laila.pet_symptom_tracker.entities.blacklistedword;

import com.laila.pet_symptom_tracker.entities.blacklistedword.dto.PostBlackListedWord;
import com.laila.pet_symptom_tracker.mainconfig.Routes;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping(Routes.BLACK_LISTED_WORDS)
public class BlackListedWordController {
  private final BlackListedWordService blackListedWordService;

  @PostMapping
  public ResponseEntity<BlackListedWord> create(@RequestBody PostBlackListedWord word) {
    BlackListedWord createdWord = blackListedWordService.create(word);

    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(createdWord.getId())
            .toUri();

    return ResponseEntity.created(location).body(createdWord);
  }

  @GetMapping
  public List<BlackListedWord> getAll() {
    return blackListedWordService.getAll();
  }

  @GetMapping("/{id}")
  public BlackListedWord getById(@PathVariable Long id) {
    return blackListedWordService.getById(id);
  }

  @PatchMapping("/{id}")
  public BlackListedWord update(@PathVariable Long id, @RequestBody PostBlackListedWord word) {
    return blackListedWordService.update(id, word);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    blackListedWordService.delete(id);
    return ResponseEntity.ok().build();
  }
}

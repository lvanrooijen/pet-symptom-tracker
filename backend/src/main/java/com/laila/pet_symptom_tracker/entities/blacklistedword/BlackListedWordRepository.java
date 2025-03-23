package com.laila.pet_symptom_tracker.entities.blacklistedword;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlackListedWordRepository extends JpaRepository<BlackListedWord, Long> {
  Optional<BlackListedWord> findByWordIgnoreCase(String word);
}

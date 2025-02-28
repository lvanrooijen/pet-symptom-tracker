package com.laila.pet_symptom_tracker.entities.breed;

import com.laila.pet_symptom_tracker.entities.pettype.PetType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BreedRepository extends JpaRepository<Breed, Long> {
  public List<Breed> findByPetType(PetType type);

  public List<Breed> findByPetTypeId(Long typeId);
}

package com.laila.pet_symptom_tracker.entities.petbreed;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetBreedRepository extends JpaRepository<PetBreed, Long> {}

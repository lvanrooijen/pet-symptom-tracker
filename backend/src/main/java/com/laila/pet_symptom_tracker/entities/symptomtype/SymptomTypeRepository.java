package com.laila.pet_symptom_tracker.entities.symptomtype;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SymptomTypeRepository extends JpaRepository<SymptomType, Long> {}

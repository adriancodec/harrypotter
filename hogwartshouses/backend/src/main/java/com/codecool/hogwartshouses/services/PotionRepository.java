package com.codecool.hogwartshouses.services;


import com.codecool.hogwartshouses.data.Potion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PotionRepository extends JpaRepository<Potion, Long> {
    //List<Potion> findAllPotionsWithStudentId(Long studentId);
    Optional<Potion> findById(Long potionId);
    List<Potion> findByStudentId(Long userId);
}

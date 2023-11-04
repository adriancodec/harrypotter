package com.codecool.hogwartshouses.repositories;


import com.codecool.hogwartshouses.data.Potion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PotionRepository extends JpaRepository<Potion, Long> {

    List<Potion> findByStudentId(Long userId);
}

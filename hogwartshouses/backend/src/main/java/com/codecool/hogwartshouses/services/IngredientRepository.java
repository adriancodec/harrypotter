package com.codecool.hogwartshouses.services;

import com.codecool.hogwartshouses.data.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    Optional<Ingredient> findByName(final String name);

}

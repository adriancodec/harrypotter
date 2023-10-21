package com.codecool.hogwartshouses.services;

import com.codecool.hogwartshouses.data.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}

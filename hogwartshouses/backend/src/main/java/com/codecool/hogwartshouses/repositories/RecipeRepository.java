package com.codecool.hogwartshouses.repositories;

import com.codecool.hogwartshouses.data.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}

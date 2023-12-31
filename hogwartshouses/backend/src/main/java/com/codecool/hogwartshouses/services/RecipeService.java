package com.codecool.hogwartshouses.services;

import com.codecool.hogwartshouses.data.Recipe;
import com.codecool.hogwartshouses.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeService(final RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    public Recipe saveRecipe(final Recipe recipe) {
        return recipeRepository.save(recipe);
    }
}

package com.codecool.hogwartshouses.controllers;

import com.codecool.hogwartshouses.data.Recipe;
import com.codecool.hogwartshouses.services.RecipeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("recipes")
public class RecipesController {

    private final RecipeService recipeService;

    public RecipesController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    List<Recipe> getAllRecipes(){
        return recipeService.getAllRecipes();
    }

    @PostMapping
    Recipe saveRecipe(@RequestBody Recipe recipe){
        return recipeService.saveRecipe(recipe);
    }
}

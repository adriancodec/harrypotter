package com.codecool.hogwartshouses.controllers;

import com.codecool.hogwartshouses.data.Ingredient;
import com.codecool.hogwartshouses.services.IngredientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("ingredients")
public class IngredientsController {
    private final IngredientService ingredientService;

    public IngredientsController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping
    List<Ingredient> getAllIngredients(){
        return ingredientService.getAllIngredients();
    }

    @PostMapping
    Ingredient saveIngredient(@RequestBody Ingredient ingredient) {
        return ingredientService.saveIngredient(ingredient);
    }
}

package com.codecool.hogwartshouses.controllers;

import com.codecool.hogwartshouses.data.Ingredient;
import com.codecool.hogwartshouses.data.Potion;
import com.codecool.hogwartshouses.data.Recipe;
import com.codecool.hogwartshouses.exceptions.potionExceptions.*;
import com.codecool.hogwartshouses.services.PotionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("potions")
public class PotionsController {

    private final PotionService potionService;

    public PotionsController(PotionService potionService) {
        this.potionService = potionService;
    }

    @GetMapping
    List<Potion> getAllPotions(){
        return potionService.getAllPotions();
    }

    @PostMapping
    Potion savePotion(@RequestBody Potion potion) throws UserNotAttachedToPotionException, IngredientsMissingException, RecipeShouldBeNullWhenCreatingException {
        return potionService.validateAndSavePotion(potion);
    }

    @GetMapping("{studentId}")
    List<Potion> getAllPotionsForUser(@PathVariable Long studentId){
        return potionService.findByStudentId(studentId);
    }

    @PostMapping("brew")
    Potion brewPotion(@RequestBody Potion potion) throws UserNotAttachedToPotionException {
        return potionService.brewPotion(potion);
    }

    @PutMapping("{potionId}/add")
    Potion addIngredientToPotion(@PathVariable Long potionId, @RequestBody Ingredient ingredient) throws PotionNotFoundException, UserNotAttachedToPotionException, MaxIngredientReachedException {
        return potionService.addIngredientToPotion(potionId, ingredient);
    }

    @GetMapping("{potionId}/help")
    List<Recipe> getAllRecipesWithPotionIngredients(@PathVariable Long potionId) throws PotionNotFoundException, PotionIsNotBrewAnymoreException {
        return potionService.getRecipesContainingThisPotionIngredients(potionId);
    }
}

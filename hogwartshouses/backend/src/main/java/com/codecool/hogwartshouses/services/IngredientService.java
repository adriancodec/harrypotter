package com.codecool.hogwartshouses.services;

import com.codecool.hogwartshouses.data.Ingredient;
import com.codecool.hogwartshouses.repositories.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    public IngredientService(final IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public List<Ingredient> getAllIngredients(){
        return ingredientRepository.findAll();
    }

    //get ingredient from database or create if not exists yet
    public Ingredient getOrCreateOneIngredient(final Ingredient ingredient) {
        return ingredientRepository.findByName(ingredient.getName()).orElseGet(() -> ingredientRepository.save(ingredient));
    }
}

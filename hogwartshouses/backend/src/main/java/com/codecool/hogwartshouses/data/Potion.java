package com.codecool.hogwartshouses.data;

import com.codecool.hogwartshouses.exceptions.potionExceptions.IngredientsMissingException;
import com.codecool.hogwartshouses.exceptions.potionExceptions.RecipeShouldBeNullWhenCreatingException;
import com.codecool.hogwartshouses.exceptions.potionExceptions.UserNotAttachedToPotionException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

import static com.codecool.hogwartshouses.constants.Constants.*;

@Entity
public class Potion extends Brewable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brewingStatus;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    public Potion(String name, Student student, List<Ingredient> ingredients) {
        super(name, student, ingredients);
    }
    public Potion() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrewingStatus() {
        return brewingStatus;
    }

    public void setBrewingStatus(String brewingStatus) {
        this.brewingStatus = brewingStatus;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    //add ingredients to potion field
    public void addIngredient(Ingredient ingredient) {
        this.getIngredients().add(ingredient);
    }

    //change potion fields if it is a replica
    public void handleBrew() {
        this.setBrewingStatus(BREW_STATUS_BREW);
    }

    //change potion fields if it is a replica
    public void handleReplica(final Recipe recipe) {
        this.setBrewingStatus(BREW_STATUS_REPLICA);
        this.setRecipe(recipe);
    }

    //change potion fields if it is a discovery
    public void handleDiscovery(final Recipe recipe) {
        this.setBrewingStatus(BREW_STATUS_DISCOVERY);
        this.setRecipe(recipe);
    }

    //validate if there is a user id associated with the potion
    public void validateUser() throws UserNotAttachedToPotionException {
        if(this.getStudent()==null) {
            throw new UserNotAttachedToPotionException();
        }
    }

    //there should be at least one ingredient associated with the potion when creating a new one directly
    public void validateIngredients() throws IngredientsMissingException {
        if(this.getIngredients()==null || this.getIngredients().isEmpty() ) {
            throw new IngredientsMissingException();
        }
    }

    //recipe should be null when creating a new one directly, the backend handles the recipe creation
    public void validateRecipe() throws RecipeShouldBeNullWhenCreatingException {
        if(this.getRecipe()!=null) {
            throw new RecipeShouldBeNullWhenCreatingException();
        }
    }


}

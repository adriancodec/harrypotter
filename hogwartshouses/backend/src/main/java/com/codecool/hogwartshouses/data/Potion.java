package com.codecool.hogwartshouses.data;

import com.codecool.hogwartshouses.exceptions.potionExceptions.IngredientsMissingException;
import com.codecool.hogwartshouses.exceptions.potionExceptions.RecipeShouldBeNullWhenCreatingException;
import com.codecool.hogwartshouses.exceptions.potionExceptions.UserNotAttachedToPotionException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.codecool.hogwartshouses.constants.Constants.BREW_STATUS_DISCOVERY;
import static com.codecool.hogwartshouses.constants.Constants.BREW_STATUS_REPLICA;

@Entity
public class Potion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long studentId;

    //@ManyToMany()//cascade=CascadeType.ALL
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST/*, cascade = CascadeType.PERSIST*/)//because of creating potions in application Runner. Can be reverted to above code in comment
    private List<Ingredient> ingredients = new ArrayList<>();

    private String brewingStatus;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    public Potion() {
    }

    public Potion(String name, Long studentId, List<Ingredient> ingredients) {
        this.name = name;
        this.studentId = studentId;
        this.ingredients = ingredients;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
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
        this.ingredients.add(ingredient);
    }

    //change potion fields if it is a replica
    public void handleReplica(final Recipe recipe) {
        System.out.println("POTION IS A REPLICA: ");
        this.setBrewingStatus(BREW_STATUS_REPLICA);
        this.setRecipe(recipe);
    }

    //change potion fields if it is a discovery
    public void handleDiscovery(final Recipe recipe) {
        System.out.println("POTION IS A DISCOVERY: " + this.getBrewingStatus());
        this.setBrewingStatus(BREW_STATUS_DISCOVERY);
        this.setRecipe(recipe);
    }


    //to get the correct ingredient list (if there is recipe, then we get the recipe ingredients, otherwise the potion ingredients)
    @JsonIgnore
    @Transient
    public List<Ingredient> getIngredientsList() {
        return this.getRecipe()!=null ? this.getRecipe().getIngredients() : this.getIngredients();
    }

    //validate if there is a user id associated with the potion
    public void validateUser() throws UserNotAttachedToPotionException {
        if(this.getStudentId()==null) {
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

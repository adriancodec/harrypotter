package com.codecool.hogwartshouses.services;

import com.codecool.hogwartshouses.data.Ingredient;
import com.codecool.hogwartshouses.data.Potion;
import com.codecool.hogwartshouses.data.Recipe;
import com.codecool.hogwartshouses.data.Student;
import com.codecool.hogwartshouses.exceptions.potionExceptions.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.codecool.hogwartshouses.constants.Constants.*;

@Service
public class PotionService {

    private final PotionRepository potionRepository;
    private final StudentRepository studentRepository;
    private final RecipeRepository recipeRepository;

    private final IngredientRepository ingredientRepository;

    public PotionService(PotionRepository potionRepository, StudentRepository studentRepository, RecipeRepository recipeRepository, IngredientRepository ingredientRepository) {
        this.potionRepository = potionRepository;
        this.studentRepository = studentRepository;
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
    }

    public List<Potion> getAllPotions() {
        return potionRepository.findAll();
    }

    //create a new potion without any ingredients
    public Potion brewPotion(Potion potion) throws UserNotAttachedToPotionException {
        potion.validateUser();
        potion.setBrewingStatus(BREW_STATUS_BREW);
        potion.setIngredients(new ArrayList<>());
        return potionRepository.save(potion);
    }

    //creating potion with a new potion object and maybe new ingredients
    public Potion validateAndSavePotion(Potion potion) throws UserNotAttachedToPotionException, IngredientsMissingException, RecipeShouldBeNullWhenCreatingException {
        potion.validateUser();
        potion.validateIngredients();
        potion.validateRecipe();

        potion.setIngredients(getOrCreateIngredientsByName(potion.getIngredients()));

        if(potion.getIngredientsList().size()<MAX_INGREDIENTS){
            System.out.println("POTION IS A BREW");
            potion.setBrewingStatus(BREW_STATUS_BREW);
        } else {
            handleReplicaOrDiscovery(potion);
        }

        return potionRepository.save(potion);
    }

    public Potion addIngredientToPotion(final Long potionId, final Ingredient newIngredient ) throws PotionNotFoundException, UserNotAttachedToPotionException, MaxIngredientReachedException {
        Optional<Potion> potionById = potionRepository.findById(potionId);
        if(potionById.isEmpty()){
            throw new PotionNotFoundException();
        } else {
            Potion potion = potionById.get();
            if(potion.getIngredientsList().size()>=MAX_INGREDIENTS){
                throw new MaxIngredientReachedException();
            } else {
                potion.addIngredient(getOrCreateOneIngredient(newIngredient));
                if(potion.getIngredientsList().size()==MAX_INGREDIENTS){
                    handleReplicaOrDiscovery(potion);
                }
            }
            return potionRepository.save(potion);
        }
    }

    private void handleReplicaOrDiscovery(Potion potion) throws UserNotAttachedToPotionException {
        List<Recipe> allRecipes = recipeRepository.findAll();
        final Optional<Recipe> replicaRecipeFound = getRecipeIfExistWithSameIngredients(allRecipes, potion.getIngredientsList());
        if(replicaRecipeFound.isPresent()){
            potion.handleReplica(replicaRecipeFound.get());
        } else {
            potion.handleDiscovery(createRecipe(potion, allRecipes));
        }
    }

    private Optional<Recipe> getRecipeIfExistWithSameIngredients(List<Recipe> recipesList, List<Ingredient> ingredients) {
        return recipesList.stream().filter((recipe) -> (getRecipeWithExactSameIngredients(ingredients, recipe.getIngredients()))).findFirst();
    }

    //compare the two lists of Ingredients if they are exactly the same
    private boolean getRecipeWithExactSameIngredients(List<Ingredient> list1, List<Ingredient> list2) {
        return list1.size() == list2.size() && new HashSet<>(list1).containsAll(list2);
    }

    //get ingredient from database or create if not exists yet
    public Ingredient getOrCreateOneIngredient(Ingredient ingredient){
        return ingredientRepository.findByName(ingredient.getName()).orElseGet(() -> ingredientRepository.save(ingredient));
    }

    //create a recipe
    private Recipe createRecipe(final Potion potion, final List<Recipe> recipesList) throws UserNotAttachedToPotionException {
        Optional<Student> studentById = studentRepository.findStudentById(potion.getStudentId());
        if(studentById.isPresent()){
            final Student student = studentById.get();
            final String studentName = student.getName();
            final int countOfDiscoveriesFromStudent = recipesList.stream().filter((recipe) -> (recipe.getStudent().getId().equals(potion.getStudentId()))).toList().size();
            final int newRecipeNumber = countOfDiscoveriesFromStudent + 1;
            final String recipeName = studentName + "'s discovery #" + newRecipeNumber;
            final Recipe recipe = new Recipe(recipeName, student, potion.getIngredients());
            potion.setIngredients(new ArrayList<>());
            System.out.println("HERE COMES the SHARED ERROR");
            return recipeRepository.save(recipe);
        } else {
            throw new UserNotAttachedToPotionException();
        }
    }

    //get list of ingredients from database or create if not exists yet
    @Transactional
    public List<Ingredient> getOrCreateIngredientsByName(List<Ingredient> ingredients) {
        List<Ingredient> returnIngredients = new ArrayList<>();
        for (Ingredient ingredient : ingredients) {
            returnIngredients.add(getOrCreateOneIngredient(ingredient));
        }
        return returnIngredients;
    }

    //This is for getting the List of potion for a user
    public List<Potion> findByStudentId(Long userId){
        return potionRepository.findByStudentId(userId);
    }

    //return list of recipes, which contains the same ingredients this potion with potionId has
    public List<Recipe> getRecipesContainingThisPotionIngredients(Long potionId) throws PotionNotFoundException, PotionIsNotBrewAnymoreException {
        Optional<Potion> potionById = potionRepository.findById(potionId);
        if(potionById.isEmpty()){
            throw new PotionNotFoundException();
        } else {
            List<Ingredient> potionIngredients = potionById.get().getIngredients();
            if(potionIngredients.size()<MAX_INGREDIENTS) {
                return getRecipesContainingTheseIngredients(potionIngredients, recipeRepository.findAll());
            } else {
                throw new PotionIsNotBrewAnymoreException();
            }
        }
    }

    //return list of similar recipes, which contains the same ingredients potion ingredients
    public List<Recipe> getRecipesContainingTheseIngredients(List<Ingredient> potionIngredientsList, List<Recipe> allRecipesList) {
        List<Recipe> similarRecipes = new ArrayList<>();
        for(Recipe recipe : allRecipesList){
            List<Ingredient> ingredientsOfRecipe = recipe.getIngredients();
            if(new HashSet<>(ingredientsOfRecipe).containsAll(potionIngredientsList)){
                similarRecipes.add(recipe);
            }
        }
        return similarRecipes;
    }

}

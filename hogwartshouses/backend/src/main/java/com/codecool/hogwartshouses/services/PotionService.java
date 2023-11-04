package com.codecool.hogwartshouses.services;

import com.codecool.hogwartshouses.data.Ingredient;
import com.codecool.hogwartshouses.data.Potion;
import com.codecool.hogwartshouses.data.Recipe;
import com.codecool.hogwartshouses.data.Student;
import com.codecool.hogwartshouses.exceptions.potionExceptions.*;
import com.codecool.hogwartshouses.exceptions.studentExceptions.StudentNotFoundException;
import com.codecool.hogwartshouses.repositories.PotionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.codecool.hogwartshouses.constants.Constants.*;

@Service
public class PotionService {

    private final PotionRepository potionRepository;
    private final StudentService studentService;
    private final RecipeService recipeService;
    private final IngredientService ingredientService;

    public PotionService(final PotionRepository potionRepository,
                         final StudentService studentService,
                         final RecipeService recipeService,
                         final IngredientService ingredientService) {
        this.potionRepository = potionRepository;
        this.studentService = studentService;
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
    }

    public List<Potion> getAllPotions() {
        return potionRepository.findAll();
    }

    //create a new potion without any ingredients
    public Potion brewPotion(final Potion potion) throws UserNotAttachedToPotionException {
        potion.validateUser();
        potion.handleBrew();
        return potionRepository.save(potion);
    }

    //create a potion with a new potion object and maybe new ingredients
    public Potion validateAndSavePotion(final Potion potion) throws UserNotAttachedToPotionException, IngredientsMissingException, RecipeShouldBeNullWhenCreatingException, StudentNotFoundException {
        potion.validateUser();
        potion.validateIngredients();
        potion.validateRecipe();

        potion.setIngredients(getOrCreateIngredientsByName(potion.getIngredients()));

        if(potion.getIngredientsList().size()<MAX_INGREDIENTS){
            potion.handleBrew();
        } else {
            checkIfReplicaOrDiscovery(potion);
        }

        return potionRepository.save(potion);
    }

    public Potion addIngredientToPotion(final Long potionId, final Ingredient newIngredient ) throws PotionNotFoundException, UserNotAttachedToPotionException, MaxIngredientReachedException, StudentNotFoundException {
        Potion potion = getPotionById(potionId);
        if(potion.getIngredientsList().size()>=MAX_INGREDIENTS){
            throw new MaxIngredientReachedException();
        }
        potion.addIngredient(ingredientService.getOrCreateOneIngredient(newIngredient));
        if(potion.getIngredientsList().size()==MAX_INGREDIENTS){
            checkIfReplicaOrDiscovery(potion);
        }
        return potionRepository.save(potion);
    }

    private void checkIfReplicaOrDiscovery(final Potion potion) throws StudentNotFoundException {
        List<Recipe> allRecipes = recipeService.getAllRecipes();
        Optional<Recipe> replicaRecipeFound = getRecipeIfExistWithSameIngredients(allRecipes, potion.getIngredientsList());
        if(replicaRecipeFound.isPresent()){
            potion.handleReplica(replicaRecipeFound.get());
        } else {
            potion.handleDiscovery(createRecipe(potion, allRecipes));
        }
    }

    private Optional<Recipe> getRecipeIfExistWithSameIngredients(final List<Recipe> recipesList, final List<Ingredient> ingredients) {
        return recipesList.stream().filter((recipe) -> (isListOfIngredientsTheSame(ingredients, recipe.getIngredients()))).findFirst();
    }

    //compare the two lists of Ingredients if they are exactly the same
    private boolean isListOfIngredientsTheSame(final List<Ingredient> list1, final List<Ingredient> list2) {
        return list1.size() == list2.size() && new HashSet<>(list1).containsAll(list2);
    }

    //create a Recipe
    public Recipe createRecipe(final Potion potion, final List<Recipe> recipesList) throws StudentNotFoundException {
        Student student = studentService.getStudentById(potion.getStudentId());
        String studentName = student.getName();
        int countOfDiscoveriesFromStudent = recipesList.stream().filter((recipe) -> (recipe.getStudent().getId().equals(potion.getStudentId()))).toList().size();
        int newRecipeNumber = countOfDiscoveriesFromStudent + 1;
        String recipeName = String.format(POTION_DISCOVERY_TEXT, studentName, newRecipeNumber);
        Recipe recipe = new Recipe(recipeName, student, potion.getIngredients());
        return recipeService.saveRecipe(recipe);
    }

    //get list of Ingredients from database or create if it does not exist yet
    @Transactional
    public List<Ingredient> getOrCreateIngredientsByName(final List<Ingredient> ingredients) {
        return ingredients.stream()
                .map(ingredientService::getOrCreateOneIngredient)
                .collect(Collectors.toList());
    }

    //This is for getting the List of Potion for a User
    public List<Potion> getPotionByStudentId(final Long userId){
        return potionRepository.findByStudentId(userId);
    }

    //return list of Recipes, which contains the same Ingredients this Potion with potionId
    public List<Recipe> getRecipesContainingThisPotionIngredients(final Long potionId) throws PotionNotFoundException, PotionIsNotBrewAnymoreException, NoIngredientsInPotionException {
        final List<Ingredient> potionIngredients = getPotionById(potionId).getIngredients();
        if(potionIngredients.size() == 0){
            throw new NoIngredientsInPotionException();
        }
        if (potionIngredients.size() >= MAX_INGREDIENTS) {
            throw new PotionIsNotBrewAnymoreException();
        }
        return filterSimilarRecipes(potionIngredients, recipeService.getAllRecipes());
    }

    //return list of similar Recipes, which contains the same Ingredients as the Potion Ingredients
    public List<Recipe> filterSimilarRecipes(final List<Ingredient> potionIngredientsList, final List<Recipe> allRecipesList) {
        return allRecipesList.stream()
                .filter(recipe -> new HashSet<>(recipe.getIngredients()).containsAll(potionIngredientsList))
                .collect(Collectors.toList());
    }

    public Potion getPotionById(final Long potionId) throws PotionNotFoundException {
        return potionRepository.findById(potionId).orElseThrow(PotionNotFoundException::new);
    }

}

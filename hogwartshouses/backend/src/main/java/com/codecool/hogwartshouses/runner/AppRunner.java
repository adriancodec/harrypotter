package com.codecool.hogwartshouses.runner;

import com.codecool.hogwartshouses.constants.Constants;
import com.codecool.hogwartshouses.data.Ingredient;
import com.codecool.hogwartshouses.data.Potion;
import com.codecool.hogwartshouses.data.Room;
import com.codecool.hogwartshouses.data.Student;
import com.codecool.hogwartshouses.exceptions.roomExceptions.UserAleadyInRoomException;
import com.codecool.hogwartshouses.services.*;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
public class AppRunner implements ApplicationRunner {

    private final RoomRepository roomRepository;
    private final StudentRepository studentRepository;

    private final IngredientRepository ingredientRepository;

    private final RoomService roomService;

    private final PotionService potionService;

    public AppRunner(RoomRepository roomRepository, StudentRepository studentRepository, IngredientRepository ingredientRepository, RoomService roomService, PotionService potionService) {
        this.roomRepository = roomRepository;
        this.studentRepository = studentRepository;
        this.ingredientRepository = ingredientRepository;
        this.roomService = roomService;
        this.potionService = potionService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        roomRepository.save(new Room(3, 0, new ArrayList<>()));
        roomRepository.save(new Room(2, 0, new ArrayList<>()));
        roomRepository.save(new Room(4, 0, new ArrayList<>()));
        roomRepository.save(new Room(4, 0, new ArrayList<>()));
        Student student1 = new Student("Hermione Granger", Constants.ANIMAL_CAT);
        Student student2 = new Student("Ron Weasley", Constants.ANIMAL_RAT);
        Student student3 = new Student("Harry Potter", Constants.ANIMAL_OWL);
        Student student4 =new Student("Drako", Constants.ANIMAL_DOG);
        studentRepository.save(student1);
        studentRepository.save(student2);
        studentRepository.save(student3);
        studentRepository.save(student4);

        //create Potions with Ingredients
        /*List<Ingredient> listOfIngredients =new ArrayList<>();
        listOfIngredients.add(ingredientRepository.save(new Ingredient("Tomato")));
        listOfIngredients.add(ingredientRepository.save(new Ingredient("Bread")));
        listOfIngredients.add(ingredientRepository.save(new Ingredient("Milk")));
        listOfIngredients.add(ingredientRepository.save(new Ingredient("Cucumber")));
        listOfIngredients.add(ingredientRepository.save(new Ingredient("Water")));
        potionService.validateAndSavePotion(new Potion("Potion 1", 3L, listOfIngredients));
        listOfIngredients.add(ingredientRepository.save(new Ingredient("Frogskin")));
        potionService.validateAndSavePotion(new Potion("Potion 2", 3L, listOfIngredients));*/

        try {
            roomService.addStudentToRoomById(3L, student1);
        } catch (UserAleadyInRoomException e) {
            throw new RuntimeException(e);
        }

        try {
            roomService.addStudentToRoomById(2L, student3);
        } catch (UserAleadyInRoomException e) {
            throw new RuntimeException(e);
        }

        try {
            roomService.addStudentToRoomById(1L, student4);
        } catch (UserAleadyInRoomException e) {
            throw new RuntimeException(e);
        }

        //TODO ADD NEW POTIONS
    }
}

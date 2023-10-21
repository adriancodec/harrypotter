package com.codecool.hogwartshouses.data;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private Student student;

    //@ManyToMany()//cascade=CascadeType.ALL
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST/*, cascade = CascadeType.MERGE*/)//because of creating potions in application Runner. Can be reverted to above code in comment
    private List<Ingredient> ingredients = new ArrayList<>();

    public Recipe() {

    }

    public Recipe(final String name, final Student student, final List<Ingredient> ingredients) {
        this.name = name;
        this.student = student;
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

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}

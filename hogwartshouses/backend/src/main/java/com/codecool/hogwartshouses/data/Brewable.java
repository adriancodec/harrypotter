package com.codecool.hogwartshouses.data;

import jakarta.persistence.*;
import org.apache.catalina.User;

import java.util.List;

@MappedSuperclass
public abstract class Brewable {

    private String name;

    @ManyToOne
    private Student student;

    //@OneToMany(mappedBy = "brewable")
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<Ingredient> ingredients;

    public Brewable() {
    }

    public Brewable(String name, Student student, List<Ingredient> ingredients) {
        this.name = name;
        this.student = student;
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}

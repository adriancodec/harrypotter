package com.codecool.hogwartshouses.data;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Recipe extends Brewable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Recipe(final String name, final Student student, final List<Ingredient> ingredients){
        super(name, student, new ArrayList<>(ingredients));
    }

    public Recipe() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

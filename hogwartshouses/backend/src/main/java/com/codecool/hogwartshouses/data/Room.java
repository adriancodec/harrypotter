package com.codecool.hogwartshouses.data;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int cappacity;
    private int occupancy;

    @OneToMany(mappedBy = "room", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Student> studentList = new ArrayList<>();

    public Room() {
    }

    public Room(int cappacity, int occupancy, List<Student> studentList) {
        this.cappacity = cappacity;
        this.occupancy = occupancy;
        this.studentList = studentList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCappacity() {
        return cappacity;
    }

    public void setCappacity(int cappacity) {
        this.cappacity = cappacity;
    }

    public int getOccupancy() {
        return occupancy;
    }

    public void setOccupancy(int occupancy) {
        this.occupancy = occupancy;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }
}

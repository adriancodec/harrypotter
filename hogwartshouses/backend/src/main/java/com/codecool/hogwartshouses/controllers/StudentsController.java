package com.codecool.hogwartshouses.controllers;

import com.codecool.hogwartshouses.data.Student;
import com.codecool.hogwartshouses.exceptions.studentExceptions.StudentNotFoundException;
import com.codecool.hogwartshouses.services.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("students")
public class StudentsController {

    private final StudentService studentService;

    public StudentsController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping()
    List<Student> getAllStudents(){
        return studentService.getAllStudents();
    }

    @PostMapping()
    Student addStudent(@RequestBody Student student){
        return studentService.saveOneStudent(student);
    }

    @PutMapping("{studentId}")
    Student editStudent(@PathVariable Long studentId, @RequestParam String pet) throws StudentNotFoundException {
        return studentService.editOneStudent(studentId, pet);
    }

    @GetMapping("{studentId}")
    Student getStudentById(@PathVariable Long studentId) throws StudentNotFoundException {
        return studentService.getStudentById(studentId);
    }

}

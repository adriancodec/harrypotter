package com.codecool.hogwartshouses.services;

import com.codecool.hogwartshouses.data.Student;
import com.codecool.hogwartshouses.exceptions.studentExceptions.StudentNotFoundException;
import com.codecool.hogwartshouses.repositories.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(final StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student saveOneStudent(final Student student) {
        return studentRepository.save(student);
    }

    public Student getStudentById(final Long studentId) throws StudentNotFoundException {
        return studentRepository.findById(studentId).orElseThrow(StudentNotFoundException::new);
    }

    public Student editOneStudent(final Long studentId, final String pet) throws StudentNotFoundException {
        Student studentById = getStudentById(studentId);
        studentById.setPet(pet);
        return studentRepository.save(studentById);
    }
}


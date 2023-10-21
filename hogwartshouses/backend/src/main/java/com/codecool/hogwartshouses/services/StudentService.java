package com.codecool.hogwartshouses.services;

import com.codecool.hogwartshouses.data.Student;
import com.codecool.hogwartshouses.exceptions.studentExceptions.StudentNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student saveOneStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student findStudentById(Long studentId) throws StudentNotFoundException {
        return studentRepository.findById(studentId).orElseThrow(StudentNotFoundException::new);
    }
}

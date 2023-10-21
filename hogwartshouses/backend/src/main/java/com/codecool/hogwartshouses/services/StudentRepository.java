package com.codecool.hogwartshouses.services;

import com.codecool.hogwartshouses.data.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    public Optional<Student> findStudentById(Long studentId);

}

package com.codecool.hogwartshouses.repositories;

import com.codecool.hogwartshouses.data.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

}

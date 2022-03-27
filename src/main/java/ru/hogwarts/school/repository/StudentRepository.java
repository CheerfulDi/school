package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;

import java.util.List;
import java.util.Set;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Set<Student> findByAge(int age);

    Set<Student> findByAgeBetween(int min, int max);

    @Query(value = "select * from student", nativeQuery = true)
    List<Student> findAll();

    @Query(value= "select AVG(age) from student", nativeQuery = true)
    Integer getAverageStudentsAge();

    @Query(value = "select * from student order by id desc limit 5", nativeQuery = true)
    Set<Student> findLastFiveStudents();

}

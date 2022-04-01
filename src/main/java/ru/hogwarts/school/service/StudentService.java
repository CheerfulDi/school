package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;


public interface StudentService {

    Student createStudent(Student student);

    Student getStudent(Long id);

    Student editStudent(Student student);

    void deleteStudent(Long id);

    Collection<Student> getStudentsByAge(int age);

    Collection<Student> getStudentsByAgeBetween(int min, int max);

    Collection<Student> getAllStudents();

    Integer getAverageStudentsAge();

    Collection<Student> getLastFiveStudents();

    List<String> getStudentsNamesStartingWithA();

    void printStudentsNames();

}

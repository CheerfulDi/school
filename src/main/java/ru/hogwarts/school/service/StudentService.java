package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Student;

import java.util.Collection;


public interface StudentService {

    Student createStudent(Student student);

    Student getStudent(Long id);

    Student editStudent(Student student);

    void deleteStudent(Long id);

    Collection<Student> getStudentsByAge(int age);

    Collection<Student> getStudentsByAgeBetween(int min, int max);

}

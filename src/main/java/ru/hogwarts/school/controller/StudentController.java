package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student newStudent = studentService.createStudent(student);
        return ResponseEntity.ok(newStudent);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        Student student = studentService.getStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @GetMapping("/all")
    public ResponseEntity<Collection<Student>> getAllStudents() {
        Collection<Student> students = studentService.getAllStudents();
        if (students == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(students);
    }

    @GetMapping("/averageAge")
    public ResponseEntity<Integer> getAverageStudentsAge() {
        Collection<Student> students = studentService.getAllStudents();
        Integer averageAge = studentService.getAverageStudentsAge();
        if (students == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(averageAge);
    }

    @GetMapping("/lastFive")
    public ResponseEntity<Collection<Student>> getLastFiveStudents() {
        Collection<Student> students = studentService.getLastFiveStudents();
        if (students == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(students);
    }

    @GetMapping("/age/{age}")
    public ResponseEntity<Collection<Student>> getStudentsByAge(@PathVariable int age) {
        Collection<Student> students = studentService.getStudentsByAge(age);
        if (students == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(students);
    }

    @GetMapping("/find_between_age")
    public ResponseEntity<Collection<Student>> getStudentsByAgeBetween(@RequestParam int min,
                                                                       @RequestParam int max) {
        Collection<Student> students = studentService.getStudentsByAgeBetween(min, max);
        if (students == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(students);
    }

    @GetMapping("/names_start_with_A")
    public ResponseEntity<List<String>> getStudentsByNameWithLetter() {
        List<String> names = studentService.getStudentsNamesStartingWithA();
        if (names == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(names);
    }

    @GetMapping("/print_names")
    public void printStudentsNames() {
        studentService.printStudentsNames();
    }

    @PutMapping
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        Student editedStudent = studentService.editStudent(student);
        if (editedStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(editedStudent);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }
}

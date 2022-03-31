package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService{

    Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    private StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student createStudent(Student student) {
        logger.info("Was invoked method to create a student");
        return studentRepository.save(student);
    }

    @Override
    public Student getStudent(Long id) {
        logger.info("Was invoked method to find a student by id");
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    public Student editStudent(Student student) {
        logger.info("Was invoked method to edit a student");
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(Long id) {
        logger.info("Was invoked method to delete a student");
        studentRepository.deleteById(id);
    }

    @Override
    public Collection<Student> getStudentsByAge(int age) {
        logger.info("Was invoked method to find students by the certain age");
        return studentRepository.findByAge(age);
    }

    @Override
    public Collection<Student> getStudentsByAgeBetween(int min, int max) {
        logger.info("Was invoked method to find students by age between min and max value");
        return studentRepository.findByAgeBetween(min, max);
    }

    @Override
    public Collection<Student> getAllStudents() {
        logger.info("Was invoked method to get a list of all students");
        return studentRepository.findAll();
    }

    @Override
    public Integer getAverageStudentsAge() {
        logger.info("Was invoked method to find an average age of all students");
        return studentRepository.getAverageStudentsAge();
    }

    @Override
    public Collection<Student> getLastFiveStudents() {
        logger.info("Was invoked method to find last five students from the list");
        return studentRepository.findLastFiveStudents();
    }

    @Override
    public List<String> getStudentsNamesStartingWithA() {
        logger.info("Was invoked method to find all students names which start with the letter A");
        return studentRepository.findAll().stream()
                .parallel()
                .map(Student::getName)
                .map(String::toUpperCase)
                .filter(n -> n.startsWith("A"))
                .sorted()
                .collect(Collectors.toList());
    }
}

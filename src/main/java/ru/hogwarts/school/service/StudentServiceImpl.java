package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService{

    HashMap<Long, Student> students = new HashMap<>();
    private long id = 0;

    @Override
    public Student createStudent(Student student) {
        student.setId(id++);
        students.put(id, student);
        return student;
    }

    @Override
    public Student getStudent(Long id) {
        if (students.containsKey(id)) {
            return students.get(id);
        }
        return null;
    }

    @Override
    public Student editStudent(Student student) {
        if (students.containsKey(student.getId())) {
            students.put(student.getId(), student);
            return student;
        }
        return null;
    }

    @Override
    public Student deleteStudent(Long id) {
        if (students.containsKey(id)) {
            return students.remove(id);
        }
        return null;
    }

    @Override
    public Collection<Student> getStudentsByAge(int age) {
            return students.values().stream()
                    .filter(s-> s.getAge() == age)
                    .collect(Collectors.toList());
        }

}

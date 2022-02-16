package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService{

    HashMap<Long, Faculty> faculties = new HashMap<>();
    private long id = 0;

    @Override
    public Faculty createFaculty(Faculty faculty) {
        faculty.setId(id++);
        faculties.put(id, faculty);
        return faculty;
    }

    @Override
    public Faculty getFaculty(Long id) {
        if (faculties.containsKey(id)) {
            return faculties.get(id);
        }
        return null;
    }

    @Override
    public Faculty editFaculty(Faculty faculty) {
        if (faculties.containsKey(faculty.getId())) {
            faculties.put(faculty.getId(), faculty);
            return faculty;
        }
        return null;
    }

    @Override
    public Faculty deleteFaculty(Long id) {
        return faculties.remove(id);
    }

    @Override
    public Collection<Faculty> getFacultiesByColor(String color) {
        return faculties.values().stream()
                .filter(f-> Objects.equals(f.getColor(), color))
                .collect(Collectors.toList());
    }

}

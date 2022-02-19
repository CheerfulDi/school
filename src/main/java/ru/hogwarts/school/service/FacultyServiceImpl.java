package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;

@Service
public class FacultyServiceImpl implements FacultyService{


    private FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }


    @Override
    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty getFaculty(Long id) {
        return facultyRepository.findById(id).orElse(null);
    }

    @Override
    public Faculty editFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    @Override
    public void deleteFaculty(Long id) {
        facultyRepository.deleteById(id);

    }

    @Override
    public Collection<Faculty> getFacultiesByColor(String color) {
        return facultyRepository.findByColor(color);
    }

}

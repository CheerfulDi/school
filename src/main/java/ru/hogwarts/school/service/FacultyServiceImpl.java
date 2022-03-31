package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.Comparator;

@Service
public class FacultyServiceImpl implements FacultyService{

    Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);

    private FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }


    @Override
    public Faculty createFaculty(Faculty faculty) {
        logger.info("Was invoked method to create a faculty");
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty getFaculty(Long id) {
        logger.info("Was invoked method to find a faculty by id");
        return facultyRepository.findById(id).orElse(null);
    }

    @Override
    public Faculty editFaculty(Faculty faculty) {
        logger.info("Was invoked method to edit a faculty");
        return facultyRepository.save(faculty);
    }

    @Override
    public void deleteFaculty(Long id) {
        logger.info("Was invoked method to delete a faculty");
        facultyRepository.deleteById(id);

    }

    @Override
    public Collection<Faculty> getFaculties(String color, String name) {
        logger.info("Was invoked method to get a list of all faculties");
        return facultyRepository.findByColorIgnoreCaseOrNameIgnoreCase(color, name);
    }

    @Override
    public String getTheLongestName() {
        logger.info("Was invoked method to find the longest faculties name");
        return facultyRepository.findAll().stream()
                .map(Faculty::getName)
                .max(Comparator.comparingInt(String::length))
                .orElse(null);
    }


}

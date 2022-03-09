package ru.hogwarts.school;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyServiceImpl;

import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.hogwarts.school.TestConstants.*;

@WebMvcTest(controllers = FacultyController.class)
public class FacultyControllerTest {

    private JSONObject facultyObject;

    private Faculty faculty;
    private Faculty faculty2;
    private Set<Faculty> faculties;


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyRepository facultyRepository;

    @SpyBean
    private FacultyServiceImpl facultyServiceImpl;

    @InjectMocks
    private FacultyController facultyController;


    @BeforeEach
    public void setUpForTests() throws Exception {

        facultyObject = new JSONObject();
        facultyObject.put("name", NAME);
        facultyObject.put("color", COLOR);

        faculty = new Faculty();
        faculty.setName(NAME);
        faculty.setId(ID);
        faculty.setColor(COLOR);

        faculty2 = new Faculty();
        faculty2.setName(NAME_2);
        faculty2.setId(ID_2);
        faculty2.setColor(COLOR);

        faculties = Set.of(faculty, faculty2);

    }

    @Test
    public void testCreateFaculty() throws Exception {
        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/faculty")
                .content(facultyObject.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.name").value(NAME))
                .andExpect(jsonPath("$.color").value(COLOR));
    }

    @Test
    public void testGetFaculty() throws Exception {
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/" + ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.name").value(NAME))
                .andExpect(jsonPath("$.color").value(COLOR));
    }

    @Test
    public void testGetFaculties() throws Exception {
        when(facultyRepository.findByColorIgnoreCaseOrNameIgnoreCase(anyString(), anyString())).thenReturn(faculties);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/sort/?color=" + COLOR + "&name=" + NAME)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*]").exists())
                .andExpect(jsonPath("$.[*].id").isNotEmpty())
                .andExpect(jsonPath("$.[*].name").isNotEmpty())
                .andExpect(jsonPath("$.[*].color").isNotEmpty());
    }

    @Test
    public void testEditFaculty() throws Exception {
        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty2);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID_2))
                .andExpect(jsonPath("$.name").value(NAME_2))
                .andExpect(jsonPath("$.color").value(COLOR));
    }

    @Test
    public void testDeleteFaculty() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/" + ID))
                .andExpect(status().isOk());
    }

}

package ru.hogwarts.school;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Student;

import java.util.ArrayList;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {

    private Student student;
    private long studentId;

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    TestRestTemplate restTemplate;

    @BeforeEach
    void setUpForTests() {
        student = new Student();

        student.setId(1L);
        student.setName("Ally");
        student.setAge(21);
    }


    @Test
    public void contextLoads() throws Exception {
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    public void testGetStudent() throws Exception {
        studentId = restTemplate.postForObject("http://localhost:" + port + "/student", student, Student.class).getId();
        Assertions.
                assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/" + studentId, Student.class))
                .isEqualTo(student);
    }

    @Test
    public void testGetStudentByAgeBetween() throws Exception {

        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/find_between_age" + "?min=" + 20 + "&max=" + 30, ArrayList.class).size())
                .isGreaterThan(0);
    }

    @Test
    public void testGetStudentByAge() throws Exception {

        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/age/", Student.class))
                .isNotNull();
    }

    @Test
    public void testCreateStudent() throws Exception {

        Assertions.
                assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/student", student, String.class))
                .isNotNull();

        Assertions.
                assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/student", student, Student.class))
                .isEqualTo(student);

    }

    @Test
    void testDeleteStudent() {
        studentId = restTemplate.postForObject("http://localhost:" + port + "/student", student, Student.class).getId();
        student.setId(studentId);
        restTemplate.delete("http://localhost:" + port + "/student" + studentId);

        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student" + studentId, Student.class))
                .isNotIn(student);
    }

    @Test
    public void testEditStudent() throws Exception {
        student.setAge(30);

        studentId = restTemplate.postForObject("http://localhost:" + port + "/student", student, Student.class).getId();

        student.setId(studentId);

        restTemplate.put("http://localhost:" + port + "/student", student);

        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student" + studentId, Student.class))
                .isNotNull();
    }
}


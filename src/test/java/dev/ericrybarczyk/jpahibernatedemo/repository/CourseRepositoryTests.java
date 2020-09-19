package dev.ericrybarczyk.jpahibernatedemo.repository;

import dev.ericrybarczyk.jpahibernatedemo.JpaHibernateDemoApplication;
import dev.ericrybarczyk.jpahibernatedemo.entity.Course;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = JpaHibernateDemoApplication.class)
class CourseRepositoryTests {

    @Autowired
    private CourseRepository repository;

    @BeforeEach
    void setUp() {

    }

    @Test
    void findById_basicTestCase() throws Exception {
        Course result = repository.findById(10001L);
        assertEquals("first course", result.getName());
    }

}
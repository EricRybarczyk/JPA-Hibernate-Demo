package dev.ericrybarczyk.jpahibernatedemo.repository;

import dev.ericrybarczyk.jpahibernatedemo.JpaHibernateDemoApplication;
import dev.ericrybarczyk.jpahibernatedemo.entity.Passport;
import dev.ericrybarczyk.jpahibernatedemo.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = JpaHibernateDemoApplication.class)
class StudentRepositoryTests {

    @Autowired
    private StudentRepository studentRepository;

    private static final String FIRST_NAME = "firstname";
    private static final String LAST_NAME = "lastname";
    private static final String PASSPORT_NUMBER = "ABC123456";

    @Test
    @DirtiesContext
    void saveStudentWithPassport_basicTest() throws Exception {
        Passport passport = new Passport(PASSPORT_NUMBER);
        Student student = new Student(FIRST_NAME, LAST_NAME);

        studentRepository.save(student, passport);

        assertEquals(passport.getId(), student.getPassport().getId());

    }

    @Test
    @DirtiesContext
    void saveStudentWithPassport_retrieveSavedObjectsHavePassportRelationship() throws Exception {
        Passport passport = new Passport(PASSPORT_NUMBER);
        Student student = new Student(FIRST_NAME, LAST_NAME);

        studentRepository.save(student, passport);

        Student resultStudent = studentRepository.findById(student.getId());
        assertNotNull(resultStudent.getPassport());
        assertEquals(resultStudent.getPassport().getId(), passport.getId());
    }

}
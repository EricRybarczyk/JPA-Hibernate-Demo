package dev.ericrybarczyk.jpahibernatedemo.repository;

import dev.ericrybarczyk.jpahibernatedemo.JpaHibernateDemoApplication;
import dev.ericrybarczyk.jpahibernatedemo.entity.Course;
import dev.ericrybarczyk.jpahibernatedemo.entity.Passport;
import dev.ericrybarczyk.jpahibernatedemo.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = JpaHibernateDemoApplication.class)
class StudentRepositoryTests {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    private static final String FIRST_NAME = "firstname";
    private static final String LAST_NAME = "lastname";
    private static final String PASSPORT_NUMBER = "ABC123456";
    private static final Long STUDENT_ID = 20001L;
    private static final int COURSE_COUNT_FOR_STUDENT_20001 = 2;

    @Test
    @Transactional
    void testStudentFetch_lazyLoadPassportIsRetrieved() throws Exception {
        Student student = studentRepository.findById(STUDENT_ID);
        assertNotNull(student.getPassport());
    }

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

    @Test
    @Transactional
    void retrieveStudentWithCourses_basicTest() throws Exception {
        Student student = studentRepository.findById(STUDENT_ID);
        assertEquals(COURSE_COUNT_FOR_STUDENT_20001, student.getCourses().size());
    }

    @Test
    @Transactional
    @DirtiesContext
    void enrollStudentInCourse_relationshipIsCreatedInBothDirections() throws Exception {
        Student student = studentRepository.save(new Student("Test", "Student"));
        Course course = courseRepository.save(new Course("Test Course"));

        Student resultStudent = studentRepository.saveEnrollment(student, course);
        assertEquals(student.getId(), resultStudent.getId());
        assertEquals(1, resultStudent.getCourses().size());
        assertEquals(course.getId(), resultStudent.getCourses().get(0).getId());

    }

}
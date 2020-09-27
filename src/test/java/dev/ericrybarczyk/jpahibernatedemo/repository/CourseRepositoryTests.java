package dev.ericrybarczyk.jpahibernatedemo.repository;

import dev.ericrybarczyk.jpahibernatedemo.JpaHibernateDemoApplication;
import dev.ericrybarczyk.jpahibernatedemo.entity.Course;
import dev.ericrybarczyk.jpahibernatedemo.entity.Review;
import dev.ericrybarczyk.jpahibernatedemo.entity.ReviewRating;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = JpaHibernateDemoApplication.class)
class CourseRepositoryTests {

    @Autowired
    private CourseRepository courseRepository;

    private static final long ID_1 = 10001L;
    private static final String FIRST_COURSE_NAME = "first course";
    private static final long ID_COURSE_WITH_NO_REVIEWS = 10003;
    private static final long ID_FOR_DELETE = 9999L;
    private static final long INVALID_ID = 8675309L;
    private static final String NEW_COURSE_NAME = "new course name";
    private static final String UPDATED_COURSE_NAME = "updated course name";
    private static final int STUDENT_COUNT_IN_COURSE_100001 = 3;

    @Test
    void findById_basicTestCase() throws Exception {
        Course course = courseRepository.findById(ID_1);
        assertEquals(FIRST_COURSE_NAME, course.getName());
    }

    @Test
    @Transactional
    void findById_firstLevelCacheDemo() throws Exception {
        Course course = courseRepository.findById(ID_1);

        // next call returns result from first level cache BECAUSE @Transactional means only a single
        // PersistenceContext for both repository calls and first level cache is scoped to the PersistenceContext.
        // The test runner execution output also shows only a single SQL query was run
        Course courseAgain = courseRepository.findById(ID_1);

        // assertions do not prove L1C was used   ¯\_(ツ)_/¯ ... oh well.
        assertEquals(FIRST_COURSE_NAME, course.getName());
        assertEquals(FIRST_COURSE_NAME, courseAgain.getName());
    }

    @Test
    @Transactional
    void findById_reviewsArePresent() throws Exception {
        Course course = courseRepository.findById(ID_1);
        assertTrue(course.getReviews().size() > 0);
    }

    @Test
    @DirtiesContext
    void deleteById_validIdValue() throws Exception {
        assertTrue(courseRepository.deleteById(ID_FOR_DELETE));
        assertNull(courseRepository.findById(ID_FOR_DELETE));
    }

    @Test
    void deleteById_invalidIdValue() throws Exception {
        assertFalse(courseRepository.deleteById(INVALID_ID));
    }

    @Test
    @DirtiesContext
    void saveCourse_newCourseEntity() throws Exception {
        Course savedCourse = courseRepository.save(new Course(NEW_COURSE_NAME));
        assertEquals(NEW_COURSE_NAME, savedCourse.getName());
    }

    @Test
    @DirtiesContext
    void saveCourse_existingCourseEntityWithModifiedValue() throws Exception {
        Course course = courseRepository.findById(ID_1);
        course.setName(UPDATED_COURSE_NAME);

        courseRepository.save(course);

        Course result = courseRepository.findById(ID_1);
        assertEquals(UPDATED_COURSE_NAME, result.getName());
    }

    @Test
    @DirtiesContext
    void createCourseWithNullName_throwsDataIntegrityViolationException() throws Exception {
        Course badCourse = new Course(null);
        assertThrows(DataIntegrityViolationException.class, () -> { courseRepository.save(badCourse); } );
    }

    @Test
    @DirtiesContext
    void updateCourse_createdDateNotModified() throws Exception {
        Course course = courseRepository.findById(ID_1);
        LocalDateTime originalCreatedDate = LocalDateTime.from(course.getCreatedDate());

        course.setName(UPDATED_COURSE_NAME);
        courseRepository.save(course);

        Course result = courseRepository.findById(ID_1);
        LocalDateTime verifyCreatedDate = LocalDateTime.from(result.getCreatedDate());

        assertEquals(originalCreatedDate, verifyCreatedDate);
    }

    @Test
    @DirtiesContext
    void updateCourse_lastUpdatedDateIsModified() throws Exception {
        Course course = courseRepository.findById(ID_1);
        LocalDateTime originalUpdatedDate = LocalDateTime.from(course.getLastUpdatedDate());

        course.setName(UPDATED_COURSE_NAME);
        courseRepository.save(course);

        Course result = courseRepository.findById(ID_1);
        LocalDateTime revisedUpdatedDate = LocalDateTime.from(result.getLastUpdatedDate());

        assertTrue(revisedUpdatedDate.isAfter(originalUpdatedDate));
    }

    @Test
    @Transactional
    @DirtiesContext
    void testSaveNewReview_ExistingCourse() throws Exception {
        Course course = courseRepository.findById(ID_COURSE_WITH_NO_REVIEWS);
        assertEquals(0, course.getReviews().size());

        Review review = new Review("test review one", ReviewRating.THREE);
        courseRepository.addReviewToCourse(ID_COURSE_WITH_NO_REVIEWS, review);

        Course updatedCourse = courseRepository.findById(ID_COURSE_WITH_NO_REVIEWS);
        assertEquals(1, updatedCourse.getReviews().size());
        assertEquals("test review one", updatedCourse.getReviews().get(0).getReviewContent());
        assertEquals(ReviewRating.THREE, updatedCourse.getReviews().get(0).getRating());
    }

    @Test
    @Transactional
    void retrieveCourseWithStudents_basicTest() throws Exception {
        Course course = courseRepository.findById(ID_1);
        assertEquals(STUDENT_COUNT_IN_COURSE_100001, course.getStudents().size());
    }

    @Test
    void testFindCoursesWithNoStudents_returnsExpectedCount() throws Exception {
        List<Course> coursesWithNoStudents = courseRepository.findCoursesWithNoStudents();
        assertEquals(2, coursesWithNoStudents.size());
    }

    @Test
    void testFindCoursesWithMultipleStudents_returnsExpectedCount() throws Exception {
        List<Course> coursesWithNoStudents = courseRepository.findCoursesWithMultipleStudents();
        assertEquals(1, coursesWithNoStudents.size());
    }

    @Test
    @Transactional
    void testGetAllCoursesSortedByNumberOfStudents_returnSortOrderIsAscending() throws Exception {
        List<Course> coursesWithNoStudents = courseRepository.getAllCoursesSortedByNumberOfStudents();
        int[] countsInOriginalOrder = new int[coursesWithNoStudents.size()];
        int[] countsSortedBySystem = new int[coursesWithNoStudents.size()];
        for (int i = 0; i < coursesWithNoStudents.size(); i++) {
            int countOfStudents = coursesWithNoStudents.get(i).getStudents().size();
            countsInOriginalOrder[i] = countOfStudents;
            countsSortedBySystem[i] = countOfStudents;
        }
        Arrays.sort(countsSortedBySystem);
        assertArrayEquals(countsSortedBySystem, countsInOriginalOrder);
    }

}
package dev.ericrybarczyk.jpahibernatedemo.jpa;

import dev.ericrybarczyk.jpahibernatedemo.JpaHibernateDemoApplication;
import dev.ericrybarczyk.jpahibernatedemo.entity.Course;
import dev.ericrybarczyk.jpahibernatedemo.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = JpaHibernateDemoApplication.class)
public class JpqlTests {

    @Autowired
    EntityManager entityManager;

    private static final long ID_1 = 10001L;

    @Test
    void typedQuery_listOfCourses_resultListContainsElements() throws Exception {
        TypedQuery<Course> courseListQuery = entityManager.createQuery("Select c from Course c where c.isDeleted = false", Course.class);
        List<Course> resultList = courseListQuery.getResultList();
        assertTrue(resultList.size() > 0);
    }

    @Test
    void typedQuery_listOfCoursesMatchingWhereClause_resultListContainsElements() throws Exception {
        TypedQuery<Course> courseListQuery = entityManager.createQuery("Select c from Course c where c.name like '%course%' and c.isDeleted = false", Course.class);
        List<Course> resultList = courseListQuery.getResultList();
        assertTrue(resultList.size() > 0);
    }

    @Test
    void typedQuery_findById_expectedEntityReturned() throws Exception {
        TypedQuery<Course> courseListQuery = entityManager.createQuery("Select c from Course c where c.id = :id", Course.class);
        courseListQuery.setParameter("id", ID_1);
        List<Course> resultList = courseListQuery.getResultList();
        assertEquals(1, resultList.size());
    }

    @Test
    void namedQuery_findAllCourses_resultListContainsElements() throws Exception {
        TypedQuery<Course> namedQuery = entityManager.createNamedQuery("query_get_all_courses", Course.class);
        List<Course> resultList = namedQuery.getResultList();
        assertTrue(resultList.size() > 0);
    }

    @Test
    void namedQuery_findFunCourses_resultListContainsSingleElement() throws Exception {
        TypedQuery<Course> namedQuery = entityManager.createNamedQuery("query_get_fun_courses", Course.class);
        List<Course> resultList = namedQuery.getResultList();
        assertEquals(1, resultList.size()); // Note: somewhat brittle, depends on specific data generated in data.sql
    }

    // examples of JOINS in JPQL queries ...  JOIN, LEFT JOIN, CROSS JOIN
    @Test
    void joinTest_retrieveCoursesJoinedWithStudents() throws Exception {
        // this just feels wrong with the unchecked assignment, but this is how the tutorial example worked
        List<Object[]> resultList = entityManager.createQuery("select c, s from Course c join c.students s where c.isDeleted = false").getResultList();
        assertTrue(resultList.size() > 0);
        for (Object[] result : resultList) {
            assertTrue(result[0] instanceof Course); // Course was first in the join, so it is the first array element type
            assertTrue(result[1] instanceof Student);
            // sample data has no students in Course ID 10003 so this assert basically verifies the join eliminated Courses with no Students
            assertNotEquals(10003L, ((Course) result[0]).getId());
        }
    }

    @Test
    void leftJoinTest_retrieveCoursesJoinedWithStudents() throws Exception {
        // again, this feels wrong with the unchecked assignment, but this is how the tutorial example worked
        List<Object[]> resultList = entityManager.createQuery("select c, s from Course c left join c.students s where c.isDeleted = false").getResultList();
        assertTrue(resultList.size() > 0);
        boolean didContainCourseWithNoStudents = false;
        for (Object[] result : resultList) {
            assertTrue(result[0] instanceof Course); // Course was first in the join, so it is the first array element type
            if (result[1] != null) {
                assertTrue(result[1] instanceof Student);
            } else {
                didContainCourseWithNoStudents = true; // null verifies the left join included a Course with no Students
            }
            assertTrue(didContainCourseWithNoStudents);
        }
    }

    @Test
    void crossJoin_verifyExpectedCount() {
        // not sure a cross join by itself will be useful very often...
        List<Object[]> resultList = entityManager.createQuery("select c, s from Course c, Student s where c.isDeleted = false").getResultList();
        // 4 courses, 3 students == 12 in a cross join
        assertEquals(12, resultList.size());
    }

}

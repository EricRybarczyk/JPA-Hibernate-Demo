package dev.ericrybarczyk.jpahibernatedemo.repository;

import dev.ericrybarczyk.jpahibernatedemo.JpaHibernateDemoApplication;
import dev.ericrybarczyk.jpahibernatedemo.entity.Course;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = JpaHibernateDemoApplication.class)
public class JpqlTests {

    @Autowired
    EntityManager entityManager;

    private static final long ID_1 = 10001L;

    @Test
    void typedQuery_listOfCourses_resultListContainsElements() throws Exception {
        TypedQuery<Course> courseListQuery = entityManager.createQuery("Select c from Course c", Course.class);
        List<Course> resultList = courseListQuery.getResultList();
        assertTrue(resultList.size() > 0);
    }

    @Test
    void typedQuery_listOfCoursesMatchingWhereClause_resultListContainsElements() throws Exception {
        TypedQuery<Course> courseListQuery = entityManager.createQuery("Select c from Course c where c.name like '%course%'", Course.class);
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

}

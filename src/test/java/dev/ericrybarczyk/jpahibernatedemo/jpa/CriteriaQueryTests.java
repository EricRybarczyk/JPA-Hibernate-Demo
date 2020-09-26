package dev.ericrybarczyk.jpahibernatedemo.jpa;

import dev.ericrybarczyk.jpahibernatedemo.JpaHibernateDemoApplication;
import dev.ericrybarczyk.jpahibernatedemo.entity.Course;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = JpaHibernateDemoApplication.class)
public class CriteriaQueryTests {

    @Autowired
    EntityManager entityManager;

    @Test
    void criteriaQuery_listOfCourses_resultListContainsElements() throws Exception {
        // This example is verbose to emphasize the discrete steps. Much of it can be inlined in real code.

        // 1. CriteriaBuilder to create a Criteria Query returning the expected result type
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Course> criteriaQuery = criteriaBuilder.createQuery(Course.class);

        // 2. Define roots for tables involved in the query
        Root<Course> courseRoot = criteriaQuery.from(Course.class);

        // 3. Define predicates etc using Criteria Builder
        //    (not used in this simple example)

        // 4. Build the TypedQuery using EntityManager and CriteriaQuery
        CriteriaQuery<Course> courseCriteriaQuery = criteriaQuery.select(courseRoot);
        TypedQuery<Course> typedQuery = entityManager.createQuery(courseCriteriaQuery);

        // 5. Get the results
        List<Course> resultList = typedQuery.getResultList();

        assertTrue(resultList.size() > 0);
    }

    @Test
    void criteriaQueryInlined_listOfCourses_resultListContainsElements() throws Exception {
        // Inlined (more compact code) version of the first test above
        CriteriaQuery<Course> criteriaQuery = entityManager.getCriteriaBuilder().createQuery(Course.class);
        List<Course> resultList = entityManager.createQuery(criteriaQuery.select(criteriaQuery.from(Course.class))).getResultList();

        assertTrue(resultList.size() > 0);
    }

    @Test
    void criteriaQuery_findFunCourses_resultListContainsSingleElement() throws Exception {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Course> criteriaQuery = criteriaBuilder.createQuery(Course.class);
        Root<Course> courseRoot = criteriaQuery.from(Course.class);

        // Define predicate using Criteria Builder
        Predicate predicate = criteriaBuilder.like(courseRoot.get("name"), "%fun%");
        criteriaQuery.where(predicate);

        CriteriaQuery<Course> courseCriteriaQuery = criteriaQuery.select(courseRoot);
        TypedQuery<Course> typedQuery = entityManager.createQuery(courseCriteriaQuery);
        List<Course> resultList = typedQuery.getResultList();

        assertEquals(1, resultList.size());
    }

    @Test
    void criteriaQuery_findCoursesWithNoStudents_resultListContainsExpectedElements() throws Exception {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Course> criteriaQuery = criteriaBuilder.createQuery(Course.class);
        Root<Course> courseRoot = criteriaQuery.from(Course.class);

        Predicate predicate = criteriaBuilder.isEmpty(courseRoot.get("students"));
        criteriaQuery.where(predicate);

        CriteriaQuery<Course> courseCriteriaQuery = criteriaQuery.select(courseRoot);
        TypedQuery<Course> typedQuery = entityManager.createQuery(courseCriteriaQuery);
        List<Course> resultList = typedQuery.getResultList();

        assertEquals(2, resultList.size());
    }

    @Test
    void criteriaQueryJoinExample_findCoursesWithNoStudents_resultListContainsExpectedElements() throws Exception {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Course> criteriaQuery = criteriaBuilder.createQuery(Course.class);
        Root<Course> courseRoot = criteriaQuery.from(Course.class);
        Join<Object, Object> join = courseRoot.join("students"); // join var is not used in this example, but is shown for completeness

        CriteriaQuery<Course> courseCriteriaQuery = criteriaQuery.select(courseRoot);
        TypedQuery<Course> typedQuery = entityManager.createQuery(courseCriteriaQuery);
        List<Course> resultList = typedQuery.getResultList();

        // this is essentially an inner join, so we effectively get the count of Students from all Courses
        assertEquals(4, resultList.size());
    }

    @Test
    void criteriaQueryLeftJoinExample_findCoursesWithNoStudents_resultListContainsExpectedElements() throws Exception {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Course> criteriaQuery = criteriaBuilder.createQuery(Course.class);
        Root<Course> courseRoot = criteriaQuery.from(Course.class);
        Join<Object, Object> join = courseRoot.join("students", JoinType.LEFT); // join var is not used in this example, but is shown for completeness

        CriteriaQuery<Course> courseCriteriaQuery = criteriaQuery.select(courseRoot);
        TypedQuery<Course> typedQuery = entityManager.createQuery(courseCriteriaQuery);
        List<Course> resultList = typedQuery.getResultList();

        // left join includes a record for each Course with no Students
        assertEquals(6, resultList.size());
    }

}
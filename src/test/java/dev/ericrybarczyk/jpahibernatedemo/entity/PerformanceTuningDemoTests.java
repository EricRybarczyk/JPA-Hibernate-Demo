package dev.ericrybarczyk.jpahibernatedemo.entity;

import dev.ericrybarczyk.jpahibernatedemo.JpaHibernateDemoApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = JpaHibernateDemoApplication.class)
public class PerformanceTuningDemoTests {

    @Autowired
    private EntityManager entityManager;

    @Test
    @Transactional
    void testDemonstratingNPlusOneProblem() throws Exception {
        // this test is a demo of the N+1 problem

        // the initial query for Courses is the 1 query we obviously expect
        List<Course> courseList = entityManager.createNamedQuery("query_get_all_courses", Course.class).getResultList();
        assertTrue(courseList.size() > 0);

        int totalStudentEnrollment = 0;
        for (Course course : courseList) {
            // by looping over the 1 query result, we cause N additional queries because getStudents() is lazy fetch by default
            // Example Session Metrics: 1954261 nanoseconds spent executing 5 JDBC statements;
            List<Student> students = course.getStudents();
            totalStudentEnrollment += students.size();
        }

        assertTrue(totalStudentEnrollment > 0);
    }

    /*  Options to resolve the N+1 problem:
        1. Make the related collection Eager Fetch, but this means Students will always be fetched with the Course lookup,
           and this is probably not what we want overall.
        2. Use the EntityGraph technique, shown below.
        3. Use the Join Fetch technique, also shown below.
     */

    @Test
    @Transactional
    void testSolvingNPlusOneProblem_EntityGraph() throws Exception {
        EntityGraph<Course> graph = entityManager.createEntityGraph(Course.class);
        graph.addSubgraph("students", Student.class);
        List<Course> courseList = entityManager
                                    .createNamedQuery("query_get_all_courses", Course.class)
                                    .setHint("javax.persistence.loadgraph", graph)
                                    .getResultList();

        // check logging of test run to verify only a single query is executed.
        // Example Session Metrics: 1202096 nanoseconds spent executing 1 JDBC statements;
        int totalStudentEnrollment = 0;
        for (Course course : courseList) {
            List<Student> students = course.getStudents();
            totalStudentEnrollment += students.size();
        }

        assertTrue(totalStudentEnrollment > 0);
    }

    @Test
    @Transactional
    void testSolvingNPlusOneProblem_JoinFetch() throws Exception {
        // the Join Fetch uses added syntax in a Named Query (defined on the Course entity)
        List<Course> courseList = entityManager.createNamedQuery("query_get_all_courses_join_fetch", Course.class).getResultList();assertTrue(courseList.size() > 0);

        int totalStudentEnrollment = 0;
        for (Course course : courseList) {
            // check logging of test run to verify only a single query is executed.
            // Example Session Metrics: 1402529 nanoseconds spent executing 1 JDBC statements;
            List<Student> students = course.getStudents();
            totalStudentEnrollment += students.size();
        }

        assertTrue(totalStudentEnrollment > 0);
    }
}

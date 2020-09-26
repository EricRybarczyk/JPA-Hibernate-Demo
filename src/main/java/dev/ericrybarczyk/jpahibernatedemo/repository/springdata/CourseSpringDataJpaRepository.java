package dev.ericrybarczyk.jpahibernatedemo.repository.springdata;

import dev.ericrybarczyk.jpahibernatedemo.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface CourseSpringDataJpaRepository extends JpaRepository<Course, Long> {
    List<Course> findByName(String name);
    List<Course> findByNameLikeIgnoreCase(String name);
    List<Course> deleteByNameLikeIgnoreCase(String name);

    @Query("select c from Course c where c.name like '%fun%'")
    List<Course> findFunCoursesJpql();

    @Query(value = "select * from courses where name like '%fun%'", nativeQuery = true)
    List<Course> findFunCoursesNative();

    @Query(name = "query_get_fun_courses")
    List<Course> findFunCoursesNamed();
}

package dev.ericrybarczyk.jpahibernatedemo.repository.springdata;

import dev.ericrybarczyk.jpahibernatedemo.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseSpringDataJpaRepository extends JpaRepository<Course, Long> {

}

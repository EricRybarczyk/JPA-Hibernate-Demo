package dev.ericrybarczyk.jpahibernatedemo.repository;

import dev.ericrybarczyk.jpahibernatedemo.entity.Course;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;

@Repository
public class CourseRepository {

    private EntityManager entityManager;

    public CourseRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Course findById(Long id) {
        return entityManager.find(Course.class, id);
    }

}

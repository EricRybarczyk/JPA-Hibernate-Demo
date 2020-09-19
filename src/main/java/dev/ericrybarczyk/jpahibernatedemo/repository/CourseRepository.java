package dev.ericrybarczyk.jpahibernatedemo.repository;

import dev.ericrybarczyk.jpahibernatedemo.entity.Course;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;

@Repository
@Transactional
public class CourseRepository {

    private EntityManager entityManager;

    public CourseRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Course findById(Long id) {
        try {
            return entityManager.find(Course.class, id);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public Course save(Course course) {
        // why not just use merge() ? see: https://stackoverflow.com/a/1070629/798642
        // key point: persist() results in the passed entity becoming managed
        if (course.getId() == null) {
            entityManager.persist(course);
        } else {
            entityManager.merge(course);
        }
        return course;
    }

    public boolean deleteById(Long id) {
        Course course = findById(id);
        if (course == null) {
            return false;
        }
        entityManager.remove(course);
        return true;
    }

}

package dev.ericrybarczyk.jpahibernatedemo.repository;

import dev.ericrybarczyk.jpahibernatedemo.entity.Course;
import dev.ericrybarczyk.jpahibernatedemo.entity.Review;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;

@Repository
@Transactional
public class CourseRepository {

    private EntityManager entityManager;
    private Logger logger;

    public CourseRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    public void playWithEntityManager() {
        logger.info("*** BEGIN: playWithEntityManager() ...");

        Course coolTechCourse = new Course("Learn Cool Tech");
        entityManager.persist(coolTechCourse);
        coolTechCourse.setName(coolTechCourse.getName() + ": Modified"); // this change will be persisted, because the entity is managed by the EntityManager
        entityManager.flush(); // causes EntityManager to process pending operations in order to persist changes

        Course mtbCourse = new Course("Mountain Bike Skills");
        entityManager.persist(mtbCourse);
        entityManager.flush();

        entityManager.detach(mtbCourse); // detach means subsequent changes will NOT be persisted, because EntityManager is no longer tracking the entity
        mtbCourse.setName(mtbCourse.getName() + ": Advanced"); // therefore, this modification will not be persisted
        entityManager.flush();

        coolTechCourse.setName("This will not be saved");
        entityManager.refresh(coolTechCourse); // reverts to persisted state by pulling state from the DB, so unsaved changes are lost
        entityManager.flush(); // not really needed, but this shows the modification is not persisted


        /*
        entityManager.clear(); // nothing after this will be persisted
        coolTechCourse.setName("not going to happen");
        mtbCourse.setName("also not happening");
        entityManager.flush();
         */

        logger.info("*** END: playWithEntityManager() ...");
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

    public void addReviewToCourse(Long courseId, Review review) {
        Course course = entityManager.find(Course.class, courseId);
        course.addReview(review);
        review.setCourse(course);
        entityManager.persist(review);
    }
}

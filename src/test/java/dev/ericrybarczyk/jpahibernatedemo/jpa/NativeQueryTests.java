package dev.ericrybarczyk.jpahibernatedemo.jpa;

import dev.ericrybarczyk.jpahibernatedemo.JpaHibernateDemoApplication;
import dev.ericrybarczyk.jpahibernatedemo.entity.Course;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = JpaHibernateDemoApplication.class)
public class NativeQueryTests {

    @Autowired
    EntityManager entityManager;

    private static final long ID_1 = 10001L;

    @Test
    void nativeQuery_findAllCourses() throws Exception {
        Query nativeQuery = entityManager.createNativeQuery("select * from courses where is_deleted=0", Course.class);
        List resultList = nativeQuery.getResultList();
        assertTrue(resultList.size() > 0);
    }

    @Test
    void nativeQuery_findById_positionalParameter() throws Exception {
        Query nativeQuery = entityManager.createNativeQuery("select * from courses where ID = ?", Course.class);
        nativeQuery.setParameter(1, ID_1);
        List resultList = nativeQuery.getResultList();
        assertEquals(1, resultList.size());
    }

    @Test
    void nativeQuery_findById_namedParameter() throws Exception {
        Query nativeQuery = entityManager.createNativeQuery("select * from courses where ID = :id", Course.class);
        nativeQuery.setParameter("id", ID_1);
        List resultList = nativeQuery.getResultList();
        assertEquals(1, resultList.size());
    }

    @Test
    @Transactional
    @DirtiesContext
    void nativeQuery_updateManyRowsAtOnce() throws Exception {
        Query nativeQuery = entityManager.createNativeQuery("update courses set last_updated_date = LOCALTIMESTAMP() where is_deleted=0", Course.class);
        int rowsUpdated = nativeQuery.executeUpdate();
        assertTrue(rowsUpdated > 0);
    }

}

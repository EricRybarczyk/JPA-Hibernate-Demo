package dev.ericrybarczyk.jpahibernatedemo.entity;

import dev.ericrybarczyk.jpahibernatedemo.JpaHibernateDemoApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = JpaHibernateDemoApplication.class)
class PassportTests {

    @Autowired
    EntityManager entityManager;

    private static final Long PASSPORT_ID = 30001L;

    @Test
    @Transactional
    void testRetrievePassportAndAssociatedStudent() throws Exception {
        Passport passport = entityManager.find(Passport.class, PASSPORT_ID);
        assertNotNull(passport);
        assertNotNull(passport.getStudent());
    }

}
package dev.ericrybarczyk.jpahibernatedemo.repository;

import dev.ericrybarczyk.jpahibernatedemo.JpaHibernateDemoApplication;
import dev.ericrybarczyk.jpahibernatedemo.entity.employees.FullTimeEmployee;
import dev.ericrybarczyk.jpahibernatedemo.entity.employees.PartTimeEmployee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = JpaHibernateDemoApplication.class)
class EmployeeRepositoryTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    private static final int FULL_TIME_SALARY = 50000;
    private static final double PART_TIME_WAGE = 25.95;
    private FullTimeEmployee fullTimeEmployee;
    private PartTimeEmployee partTimeEmployee;

    @BeforeEach
    void setUp() {
        fullTimeEmployee = new FullTimeEmployee("Jane Employee", new BigDecimal(FULL_TIME_SALARY));
        partTimeEmployee = new PartTimeEmployee("Joe Employee", new BigDecimal(String.valueOf(PART_TIME_WAGE)));
    }

    @Test
    @DirtiesContext
    void save_FullTimeEmployee() throws Exception {
        FullTimeEmployee savedEmployee = (FullTimeEmployee) employeeRepository.save(fullTimeEmployee);
        assertNotNull(savedEmployee.getId());
        assertEquals(fullTimeEmployee.getName(), savedEmployee.getName());
        assertEquals(fullTimeEmployee.getAnnualSalary(), savedEmployee.getAnnualSalary());
    }

    @Test
    @DirtiesContext
    void save_PartTimeEmployee() throws Exception {
        PartTimeEmployee savedEmployee = (PartTimeEmployee) employeeRepository.save(partTimeEmployee);
        assertNotNull(savedEmployee.getId());
        assertEquals(partTimeEmployee.getName(), savedEmployee.getName());
        assertEquals(partTimeEmployee.getHourlyWage(), savedEmployee.getHourlyWage());
    }

    @Test
    @DirtiesContext
    void findAll() throws Exception {
        employeeRepository.save(fullTimeEmployee);
        employeeRepository.save(partTimeEmployee);

        assertEquals(2, employeeRepository.findAll().size());
    }

    /* these two tests were used when Employee was set as a @MappedSuperclass
    @Test
    @DirtiesContext
    void findAllPartTimeEmployees() throws Exception {
        employeeRepository.save(partTimeEmployee);
        employeeRepository.save(new PartTimeEmployee("part timer", new BigDecimal("12.34")));

        assertEquals(2, employeeRepository.findAllPartTimeEmployees().size());
    }

    @Test
    @DirtiesContext
    void findAllFullTimeEmployees() throws Exception {
        employeeRepository.save(fullTimeEmployee);
        employeeRepository.save(new FullTimeEmployee("full timer", new BigDecimal(123)));
        employeeRepository.save(new FullTimeEmployee("employee 3", new BigDecimal(456)));

        assertEquals(3, employeeRepository.findAllFullTimeEmployees().size());
    }
     */

}
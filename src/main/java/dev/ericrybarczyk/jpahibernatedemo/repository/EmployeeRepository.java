package dev.ericrybarczyk.jpahibernatedemo.repository;

import dev.ericrybarczyk.jpahibernatedemo.entity.employees.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

@Repository
@Transactional
public class EmployeeRepository {

    private EntityManager entityManager;
    private Logger logger;

    public EmployeeRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    public Employee save(Employee employee) {
        if (employee.getId() == null) {
            entityManager.persist(employee);
        } else {
            entityManager.merge(employee);
        }
        return employee;
    }

    public List<Employee> findAll() {
        return entityManager.createQuery("select e from Employee e", Employee.class).getResultList();
    }

    /*  these two separate methods were used when Employee was set as a @MappedSuperclass - because there is no Employee table in that configuration

    public List<PartTimeEmployee> findAllPartTimeEmployees() {
        return entityManager.createQuery("select e from PartTimeEmployee e", PartTimeEmployee.class).getResultList();
    }

    public List<FullTimeEmployee> findAllFullTimeEmployees() {
        return entityManager.createQuery("select e from FullTimeEmployee e", FullTimeEmployee.class).getResultList();
    }
     */

}

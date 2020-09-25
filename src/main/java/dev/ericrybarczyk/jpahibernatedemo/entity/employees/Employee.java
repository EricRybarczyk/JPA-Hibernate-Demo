package dev.ericrybarczyk.jpahibernatedemo.entity.employees;

import javax.persistence.*;

@Entity
// @MappedSuperclass // a MappedSuperclass can not be an Entity because no table is created for the superclass
@Inheritance(strategy = InheritanceType.JOINED) // default strategy is InheritanceType.SINGLE_TABLE
public abstract class Employee {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    protected Employee() {
    }

    public Employee(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("Employee[%s]", name);
    }
}

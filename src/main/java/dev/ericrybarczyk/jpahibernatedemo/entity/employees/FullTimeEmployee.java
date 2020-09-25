package dev.ericrybarczyk.jpahibernatedemo.entity.employees;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
public class FullTimeEmployee extends Employee {

    private BigDecimal annualSalary;

    protected FullTimeEmployee() {
    }

    public FullTimeEmployee(String name, BigDecimal annualSalary) {
        super(name);
        this.annualSalary = annualSalary;
    }

    public BigDecimal getAnnualSalary() {
        return annualSalary;
    }

    public void setAnnualSalary(BigDecimal annualSalary) {
        this.annualSalary = annualSalary;
    }

    @Override
    public String toString() {
        return String.format("FullTimeEmployee[%s]", getName());
    }
}

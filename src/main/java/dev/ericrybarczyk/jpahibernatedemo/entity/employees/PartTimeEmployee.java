package dev.ericrybarczyk.jpahibernatedemo.entity.employees;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
public class PartTimeEmployee extends Employee {

    private BigDecimal hourlyWage;

    protected PartTimeEmployee() {
    }

    public PartTimeEmployee(String name, BigDecimal hourlyWage) {
        super(name);
        this.hourlyWage = hourlyWage;
    }

    public BigDecimal getHourlyWage() {
        return hourlyWage;
    }

    public void setHourlyWage(BigDecimal hourlyWage) {
        this.hourlyWage = hourlyWage;
    }

    @Override
    public String toString() {
        return String.format("PartTimeEmployee[%s]", getName());
    }
}

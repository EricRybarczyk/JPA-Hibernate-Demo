package dev.ericrybarczyk.jpahibernatedemo.entity;

import javax.persistence.*;

@Entity
@Table(name = "passports")
public class Passport {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String passportNumber;

    // mappedBy specifies the field name in the object that owns this relationship
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "passport")
    private Student student;

    protected Passport() {
    }

    public Passport(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public Long getId() {
        return id;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return passportNumber;
    }

}

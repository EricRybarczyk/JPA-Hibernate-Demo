package dev.ericrybarczyk.jpahibernatedemo.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "courses")
@NamedQueries(value = {
        @NamedQuery(name = "query_get_all_courses", query = "select c from Course c"),
        @NamedQuery(name = "query_get_fun_courses", query = "select c from Course c where c.name like '%fun%'")
})
public class Course {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @CreationTimestamp // Hibernate-specific annotations for timestamp auditing
    private LocalDateTime createdDate;

    @UpdateTimestamp // Hibernate-specific annotations for timestamp auditing
    private LocalDateTime lastUpdatedDate;

    @OneToMany(mappedBy = "course") // mappedBy specifies the field name in the object that owns this relationship
    private List<Review> reviews = new ArrayList<>();

    @ManyToMany(mappedBy = "courses") // either side can "own" the relationship in ManyToMany since the result is a join table. Also, this is lazy fetch by default.
    private List<Student> students = new ArrayList<>();

    protected Course() {
    }

    public Course(String name) {
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

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public List<Review> getReviews() {
        return Collections.unmodifiableList(reviews);
    }

    public void addReview(Review review) {
        this.reviews.add(review);
    }

    public void removeReview(Review review) {
        this.reviews.remove(review);
    }

    public List<Student> getStudents() {
        return students;
    }

    public void addStudent(Student student) {
        this.students.add(student);
    }

    public void removeStudent(Student student) {
        this.students.remove(student);
    }

    @Override
    public String toString() {
        return String.format("Course[%s]", name);
    }

}

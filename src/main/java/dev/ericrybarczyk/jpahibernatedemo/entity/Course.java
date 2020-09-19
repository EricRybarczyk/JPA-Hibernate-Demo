package dev.ericrybarczyk.jpahibernatedemo.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.time.LocalDateTime;

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

    @Override
    public String toString() {
        return String.format("Course[%s]", name);
    }

}

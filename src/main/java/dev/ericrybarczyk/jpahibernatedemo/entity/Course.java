package dev.ericrybarczyk.jpahibernatedemo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "courses")
@NamedQueries(value = {
        @NamedQuery(name = "query_get_all_courses", query = "select c from Course c where c.isDeleted = false"),
        @NamedQuery(name = "query_get_fun_courses", query = "select c from Course c where c.name like '%fun%' and c.isDeleted = false")
})
@Cacheable
@SQLDelete(sql="update courses set is_deleted=true where id=?") // Hibernate-specific annotation to configure soft-delete
@Where(clause = "is_deleted=false") // Hibernate-specific annotation to exclude soft-deleted entities
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

    private boolean isDeleted; // soft-delete support

    @PreRemove
    private void preRemove() {
        /*  Lifecycle callback, implementing soft-delete.
            The @Where annotation on this entity only updates the database, it does not modify the object itself
            because there is no connection between the @Where clause and the isDeleted field. This @PreRemove hook
            updates the object - and this includes updating the object in cache if applicable.
         */
        this.isDeleted = true;
    }

    // mappedBy specifies the field name in the object that owns this relationship,
    // cascade allows JpaRepository to persist the Review when this Course is persisted
    @OneToMany(mappedBy = "course", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Review> reviews = new ArrayList<>();

    @ManyToMany(mappedBy = "courses") // either side can "own" the relationship in ManyToMany since the result is a join table. Also, this is lazy fetch by default.
    @JsonIgnore
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

package dev.ericrybarczyk.jpahibernatedemo.entity;

import javax.persistence.*;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = true)
    private String reviewContent;

    @Column(nullable = false)
    private String rating;

    @ManyToOne
    private Course course;

    protected Review() {
    }

    public Review(String reviewContent, String rating) {
        this.reviewContent = reviewContent;
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "Review{" +
                ", rating='" + rating + '\'' +
                "reviewContent='" + reviewContent + '\'' +
                '}';
    }

}

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReviewRating rating;

    @ManyToOne // default here is Eager Fetch
    private Course course;

    protected Review() {
        rating = ReviewRating.THREE;
    }

    public Review(String reviewContent, ReviewRating rating) {
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

    public ReviewRating getRating() {
        return rating;
    }

    public void setRating(ReviewRating rating) {
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

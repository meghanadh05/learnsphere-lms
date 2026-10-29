package com.caselearn.platform.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "enrollments", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "course_id"}))
public class Enrollment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Course course;
    @Column(nullable = false)
    private int progressPercent;
    private Integer bestQuizScore;
    @Column(nullable = false, updatable = false)
    private Instant enrolledAt = Instant.now();

    public Long getId() { return id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }
    public int getProgressPercent() { return progressPercent; }
    public void setProgressPercent(int progressPercent) { this.progressPercent = progressPercent; }
    public Integer getBestQuizScore() { return bestQuizScore; }
    public void setBestQuizScore(Integer bestQuizScore) { this.bestQuizScore = bestQuizScore; }
    public Instant getEnrolledAt() { return enrolledAt; }
}


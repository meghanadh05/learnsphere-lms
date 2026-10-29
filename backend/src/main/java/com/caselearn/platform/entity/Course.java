package com.caselearn.platform.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
public class Course {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 40)
    private String code;
    @Column(nullable = false, length = 140)
    private String title;
    @Column(nullable = false, length = 80)
    private String instructor;
    @Column(nullable = false, length = 40)
    private String category;
    @Column(nullable = false, length = 1000)
    private String description;
    @Column(nullable = false)
    private int durationHours;
    @Column(nullable = false)
    private int lessonCount;
    @Column(nullable = false, length = 16)
    private String level;
    @Column(nullable = false, length = 16)
    private String accent;
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("position ASC")
    private List<QuizQuestion> questions = new ArrayList<>();

    public Long getId() { return id; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getInstructor() { return instructor; }
    public void setInstructor(String instructor) { this.instructor = instructor; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getDurationHours() { return durationHours; }
    public void setDurationHours(int durationHours) { this.durationHours = durationHours; }
    public int getLessonCount() { return lessonCount; }
    public void setLessonCount(int lessonCount) { this.lessonCount = lessonCount; }
    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }
    public String getAccent() { return accent; }
    public void setAccent(String accent) { this.accent = accent; }
    public List<QuizQuestion> getQuestions() { return questions; }
    public void addQuestion(QuizQuestion question) { questions.add(question); question.setCourse(this); }
}


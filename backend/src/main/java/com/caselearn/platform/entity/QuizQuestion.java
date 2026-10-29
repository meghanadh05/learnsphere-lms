package com.caselearn.platform.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "quiz_questions")
public class QuizQuestion {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Course course;
    @Column(nullable = false)
    private int position;
    @Column(nullable = false, length = 500)
    private String prompt;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "quiz_options", joinColumns = @JoinColumn(name = "question_id"))
    @OrderColumn(name = "option_position")
    @Column(name = "option_text", nullable = false, length = 300)
    private List<String> options = new ArrayList<>();
    @Column(nullable = false)
    private int correctOption;

    public Long getId() { return id; }
    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }
    public int getPosition() { return position; }
    public void setPosition(int position) { this.position = position; }
    public String getPrompt() { return prompt; }
    public void setPrompt(String prompt) { this.prompt = prompt; }
    public List<String> getOptions() { return options; }
    public void setOptions(List<String> options) { this.options = options; }
    public int getCorrectOption() { return correctOption; }
    public void setCorrectOption(int correctOption) { this.correctOption = correctOption; }
}


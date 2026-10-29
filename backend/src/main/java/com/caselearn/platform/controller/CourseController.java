package com.caselearn.platform.controller;

import com.caselearn.platform.dto.ApiDtos.*;
import com.caselearn.platform.service.LearningService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CourseController {
    private final LearningService learning;
    public CourseController(LearningService learning) { this.learning = learning; }

    @GetMapping("/courses") public List<CourseView> courses(Authentication auth) { return learning.list(name(auth)); }
    @GetMapping("/courses/{id}") public CourseView course(@PathVariable Long id, Authentication auth) { return learning.get(id, name(auth)); }
    @PostMapping("/courses/{id}/enroll") public CourseView enroll(@PathVariable Long id, Authentication auth) { return learning.enroll(id, auth.getName()); }
    @PostMapping("/courses/{id}/progress") public EnrollmentView progress(@PathVariable Long id, @Valid @RequestBody ProgressRequest request, Authentication auth) { return learning.updateProgress(id, auth.getName(), request.progressPercent()); }
    @PostMapping("/courses/{id}/quiz") public QuizResult quiz(@PathVariable Long id, @Valid @RequestBody QuizSubmission request, Authentication auth) { return learning.submitQuiz(id, auth.getName(), request.answers()); }
    @GetMapping("/dashboard") public DashboardView dashboard(Authentication auth) { return learning.dashboard(auth.getName()); }
    @GetMapping("/health") public MessageResponse health() { return new MessageResponse("healthy"); }
    private String name(Authentication auth) { return auth == null ? null : auth.getName(); }
}

package com.caselearn.platform.dto;

import jakarta.validation.constraints.*;
import java.time.Instant;
import java.util.List;
import java.util.Map;

public final class ApiDtos {
    private ApiDtos() {}

    public record RegisterRequest(
            @NotBlank @Size(max = 80) String name,
            @NotBlank @Email @Size(max = 160) String email,
            @NotBlank @Size(min = 8, max = 72) String password) {}

    public record LoginRequest(
            @NotBlank @Email String email,
            @NotBlank String password) {}

    public record UserView(Long id, String name, String email) {}
    public record AuthResponse(String token, UserView user) {}
    public record QuestionView(Long id, int position, String prompt, List<String> options) {}
    public record CourseView(Long id, String code, String title, String instructor, String category,
                             String description, int durationHours, int lessonCount, String level,
                             String accent, boolean enrolled, int progressPercent, Integer bestQuizScore,
                             List<QuestionView> questions) {}
    public record EnrollmentView(Long courseId, String code, String title, String instructor,
                                 String accent, int progressPercent, Integer bestQuizScore, Instant enrolledAt) {}
    public record DashboardView(UserView user, List<EnrollmentView> enrollments,
                                int coursesInProgress, int coursesCompleted, int averageQuizScore) {}
    public record ProgressRequest(@Min(0) @Max(100) int progressPercent) {}
    public record QuizSubmission(@NotNull Map<Long, Integer> answers) {}
    public record QuizResult(int correct, int total, int scorePercent, Map<Long, Boolean> results) {}
    public record MessageResponse(String message) {}
}


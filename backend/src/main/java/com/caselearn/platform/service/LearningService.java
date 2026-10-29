package com.caselearn.platform.service;

import com.caselearn.platform.dto.ApiDtos.*;
import com.caselearn.platform.entity.*;
import com.caselearn.platform.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

@Service
@Transactional(readOnly = true)
public class LearningService {
    private final CourseRepository courses;
    private final EnrollmentRepository enrollments;
    private final AuthService auth;

    public LearningService(CourseRepository courses, EnrollmentRepository enrollments, AuthService auth) {
        this.courses = courses; this.enrollments = enrollments; this.auth = auth;
    }

    public List<CourseView> list(String email) {
        User user = email == null ? null : auth.requireUser(email);
        return courses.findAll().stream().map(course -> courseView(course, enrollment(user, course), false)).toList();
    }

    public CourseView get(Long id, String email) {
        Course course = requireCourse(id);
        User user = email == null ? null : auth.requireUser(email);
        return courseView(course, enrollment(user, course), true);
    }

    @Transactional
    public CourseView enroll(Long id, String email) {
        User user = auth.requireUser(email);
        Course course = requireCourse(id);
        Enrollment enrollment = enrollments.findByUserIdAndCourseId(user.getId(), id).orElseGet(() -> {
            Enrollment created = new Enrollment(); created.setUser(user); created.setCourse(course); return enrollments.save(created);
        });
        return courseView(course, enrollment, true);
    }

    @Transactional
    public EnrollmentView updateProgress(Long id, String email, int progress) {
        Enrollment enrollment = requireEnrollment(auth.requireUser(email), id);
        enrollment.setProgressPercent(progress);
        return enrollmentView(enrollment);
    }

    @Transactional
    public QuizResult submitQuiz(Long id, String email, Map<Long, Integer> answers) {
        Enrollment enrollment = requireEnrollment(auth.requireUser(email), id);
        List<QuizQuestion> questions = enrollment.getCourse().getQuestions();
        if (questions.isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This course does not have a quiz yet.");
        int correct = 0;
        Map<Long, Boolean> results = new LinkedHashMap<>();
        for (QuizQuestion question : questions) {
            boolean isCorrect = Objects.equals(answers.get(question.getId()), question.getCorrectOption());
            results.put(question.getId(), isCorrect);
            if (isCorrect) correct++;
        }
        int score = (int) Math.round(correct * 100.0 / questions.size());
        if (enrollment.getBestQuizScore() == null || score > enrollment.getBestQuizScore()) enrollment.setBestQuizScore(score);
        if (score >= 70) enrollment.setProgressPercent(100);
        return new QuizResult(correct, questions.size(), score, results);
    }

    public DashboardView dashboard(String email) {
        User user = auth.requireUser(email);
        List<Enrollment> items = enrollments.findByUserIdOrderByEnrolledAtDesc(user.getId());
        int complete = (int) items.stream().filter(item -> item.getProgressPercent() == 100).count();
        int average = (int) Math.round(items.stream().filter(item -> item.getBestQuizScore() != null)
                .mapToInt(Enrollment::getBestQuizScore).average().orElse(0));
        return new DashboardView(AuthService.view(user), items.stream().map(this::enrollmentView).toList(),
                items.size() - complete, complete, average);
    }

    private Course requireCourse(Long id) { return courses.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found.")); }
    private Enrollment requireEnrollment(User user, Long courseId) { return enrollments.findByUserIdAndCourseId(user.getId(), courseId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Enroll in this course first.")); }
    private Enrollment enrollment(User user, Course course) { return user == null ? null : enrollments.findByUserIdAndCourseId(user.getId(), course.getId()).orElse(null); }

    private CourseView courseView(Course course, Enrollment enrollment, boolean withQuestions) {
        List<QuestionView> questions = withQuestions ? course.getQuestions().stream()
                .map(q -> new QuestionView(q.getId(), q.getPosition(), q.getPrompt(), q.getOptions())).toList() : List.of();
        return new CourseView(course.getId(), course.getCode(), course.getTitle(), course.getInstructor(), course.getCategory(),
                course.getDescription(), course.getDurationHours(), course.getLessonCount(), course.getLevel(), course.getAccent(),
                enrollment != null, enrollment == null ? 0 : enrollment.getProgressPercent(),
                enrollment == null ? null : enrollment.getBestQuizScore(), questions);
    }

    private EnrollmentView enrollmentView(Enrollment e) {
        Course c = e.getCourse();
        return new EnrollmentView(c.getId(), c.getCode(), c.getTitle(), c.getInstructor(), c.getAccent(),
                e.getProgressPercent(), e.getBestQuizScore(), e.getEnrolledAt());
    }
}


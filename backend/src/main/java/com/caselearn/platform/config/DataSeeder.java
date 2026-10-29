package com.caselearn.platform.config;

import com.caselearn.platform.entity.*;
import com.caselearn.platform.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.List;

@Configuration
public class DataSeeder {
    @Bean
    CommandLineRunner seedData(CourseRepository courses, UserRepository users, EnrollmentRepository enrollments, PasswordEncoder passwords) {
        return args -> {
            if (courses.count() == 0) {
                courses.save(course("CS-RCT", "React Component Design", "Dr. Ananya Rao", "Frontend Development",
                        "Build maintainable interfaces with components, hooks, state, routing, and accessible interaction patterns.",
                        12, 18, "Intermediate", "violet", List.of(
                                question(1, "Which hook stores component state?", List.of("useMemo", "useState", "useRef", "useEffect"), 1),
                                question(2, "Why are keys used when rendering a list?", List.of("To style list items", "To encrypt data", "To help React identify changed items", "To fetch APIs"), 2),
                                question(3, "Which approach best supports reusable UI?", List.of("One large component", "Small focused components", "Inline HTML copies", "Global variables"), 1))));
                courses.save(course("BE-SPR", "Spring MVC Essentials", "Prof. Kiran Mehta", "Backend Engineering",
                        "Design clean REST APIs with Spring MVC, layered services, validation, security, and database access.",
                        16, 22, "Intermediate", "orange", List.of(
                                question(1, "Which annotation marks a REST controller?", List.of("@Entity", "@Service", "@RestController", "@Bean"), 2),
                                question(2, "What is the role of a service layer?", List.of("Render CSS", "Hold business logic", "Create tables manually", "Start the browser"), 1),
                                question(3, "Which annotation validates a request body?", List.of("@Valid", "@Override", "@Table", "@OrderBy"), 0))));
                courses.save(course("DB-MY8", "MySQL Data Foundations", "Dr. Sana Iqbal", "Database Systems",
                        "Learn relational modelling, SQL queries, indexes, transactions, and practical schema design with MySQL 8.",
                        10, 15, "Beginner", "teal", List.of(
                                question(1, "Which constraint uniquely identifies a row?", List.of("FOREIGN KEY", "PRIMARY KEY", "CHECK", "DEFAULT"), 1),
                                question(2, "Which command reads rows from a table?", List.of("SELECT", "UPDATE", "DROP", "ALTER"), 0),
                                question(3, "What does an index primarily improve?", List.of("Query lookup speed", "Password length", "Screen layout", "Network encryption"), 0))));
                courses.save(course("WD-FST", "Full-Stack Project Lab", "Ms. Diya Nair", "Project Learning",
                        "Connect a React client, Spring backend, and MySQL database into one deployable, tested application.",
                        20, 12, "Advanced", "blue", List.of(
                                question(1, "What should never be committed to Git?", List.of("README", "Source code", "Secrets and passwords", "Tests"), 2),
                                question(2, "What does CORS control?", List.of("Cross-origin browser access", "SQL indexes", "Java compilation", "Image size"), 0),
                                question(3, "Why use a health endpoint?", List.of("Check service readiness", "Store passwords", "Write CSS", "Replace tests"), 0))));
            }

            if (!users.existsByEmailIgnoreCase("student@learnsphere.demo")) {
                User demo = new User(); demo.setName("Demo Student"); demo.setEmail("student@learnsphere.demo"); demo.setPasswordHash(passwords.encode("Demo@123"));
                users.save(demo);
                courses.findAll().stream().findFirst().ifPresent(course -> {
                    Enrollment enrollment = new Enrollment(); enrollment.setUser(demo); enrollment.setCourse(course); enrollment.setProgressPercent(45); enrollment.setBestQuizScore(67); enrollments.save(enrollment);
                });
            }
        };
    }

    private Course course(String code, String title, String instructor, String category, String description,
                          int hours, int lessons, String level, String accent, List<QuizQuestion> questions) {
        Course course = new Course(); course.setCode(code); course.setTitle(title); course.setInstructor(instructor);
        course.setCategory(category); course.setDescription(description); course.setDurationHours(hours);
        course.setLessonCount(lessons); course.setLevel(level); course.setAccent(accent); questions.forEach(course::addQuestion); return course;
    }

    private QuizQuestion question(int position, String prompt, List<String> options, int correct) {
        QuizQuestion q = new QuizQuestion(); q.setPosition(position); q.setPrompt(prompt); q.setOptions(options); q.setCorrectOption(correct); return q;
    }
}

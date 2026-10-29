# LearnSphere

LearnSphere is a full-stack online learning platform built for the case study requirement. It uses React for reusable course, quiz, and dashboard interfaces; Spring Boot/Spring MVC for authentication, enrollment, and progress logic; and MySQL for persistence.

## Features

- Student registration and login with JWT authentication
- Searchable course catalogue and course details
- Course enrollment and learning progress tracking
- Interactive quizzes with instant scoring
- Personal dashboard with enrolled courses, scores, and completion status
- Responsive, accessible UI with team details on the home page

## Stack

- Frontend: React 18, Vite, React Router
- Backend: Java 17, Spring Boot 3, Spring MVC, Spring Security, Spring Data JPA
- Database: MySQL 8 (H2 is used only for automated tests)
- Deployment: Docker, Docker Compose, Railway-compatible configuration

## Run with Docker (recommended)

1. Copy `.env.example` to `.env` and change the secrets.
2. Run `docker compose up --build`.
3. Open `http://localhost:8080`.

The production container serves the compiled React application and the Spring API from one URL. MySQL is available only to the backend container.

Demo login: `student@learnsphere.demo` / `Demo@123`

## Run for development

Start MySQL:

```bash
docker compose up db -d
```

Start the backend:

```bash
cd backend
./mvnw spring-boot:run
```

Start the frontend in a second terminal:

```bash
cd frontend
npm install
npm run dev
```

The Vite dev server proxies `/api` to `http://localhost:8080`.

## Test and build

```bash
cd backend && ./mvnw test
cd frontend && npm install && npm run build
docker compose config
```

## API overview

- `POST /api/auth/register`, `POST /api/auth/login`, `GET /api/auth/me`
- `GET /api/courses`, `GET /api/courses/{id}`
- `POST /api/courses/{id}/enroll`
- `POST /api/courses/{id}/progress`
- `POST /api/courses/{id}/quiz`
- `GET /api/dashboard`
- `GET /api/health`

## Deployment

The repository includes a multi-stage `Dockerfile` and `railway.toml`. Create a Railway project from this GitHub repository, add a MySQL service, and set the variables documented in `.env.example`. The app listens on Railway's `PORT` automatically.

## Team details

Update `frontend/src/data.js` with the three member names and roll numbers before submission.


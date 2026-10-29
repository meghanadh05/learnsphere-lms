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
- Deployment: Docker, Docker Compose, and a Render Blueprint

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

## Deployment: Render + Aiven MySQL

The repository includes a multi-stage `Dockerfile` and `render.yaml`. Render builds the React frontend, packages it into the Spring Boot application, and publishes both from a single web service.

1. Create a free Aiven for MySQL service.
2. In Aiven, open **Quick connect**, select **Java**, and copy the JDBC URI.
3. In Render, choose **New > Blueprint** and connect this GitHub repository.
4. Configure the environment variables requested by the Blueprint:

   - `SPRING_DATASOURCE_URL`: Aiven's Java JDBC URI
   - `SPRING_DATASOURCE_USERNAME`: Aiven service username, usually `avnadmin`
   - `SPRING_DATASOURCE_PASSWORD`: Aiven service password
   - `APP_CORS_ALLOWED_ORIGINS`: the final Render URL, for example `https://learnsphere-lms.onrender.com`

`JWT_SECRET` is generated securely by Render. Do not commit database credentials or production secrets to GitHub. The application automatically uses Render's `PORT` value and exposes `/api/health` for deployment health checks.

## Team details

Update `frontend/src/data.js` with the three member names and roll numbers before submission.


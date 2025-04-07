# HMCTS DTS Developer Challenge

This is a full-stack task management application built for the HMCTS Developer Challenge.

## Tech Stack

### Backend:
- Java 8+
- Spring Boot
- Spring Data JPA
- H2 (in-memory database)

### Frontend:
- React
- React Hook Form
- Axios
- Tailwind CSS (or your choice of CSS framework)

---

## Features

### Backend (Spring Boot):
- Create, read, update, delete (CRUD) tasks
- Each task includes: `title`, `description`, `status`, and `dueDate`
- RESTful API with JSON responses

### Frontend (React):
- Form to create and update tasks
- Display a list of all tasks
- Edit task status or details
- Delete tasks

---

## Getting Started

### Prerequisites
- Java 8 or above
- Node.js and npm

---

## Backend Setup (Spring Boot)

1. Clone the repo
```bash
git clone https://github.com/your-username/task-manager.git
cd task-manager
```

2. Build and run the Spring Boot application
```bash
./mvnw spring-boot:run
```

The API will be running at: `http://localhost:8080`

### API Endpoints
| Method | Endpoint         | Description           |
|--------|------------------|-----------------------|
| GET    | /tasks           | Get all tasks         |
| GET    | /tasks/{id}      | Get task by ID        |
| POST   | /tasks           | Create new task       |
| PUT    | /tasks/{id}      | Update task by ID     |
| DELETE | /tasks/{id}      | Delete task by ID     |

---

## Frontend Setup (React)

1. Navigate to the `client` folder (you’ll need to create it if not done already)
```bash
cd client
```

2. Create the React app
```bash
npx create-react-app .
```

3. Install dependencies
```bash
npm install axios react-hook-form
```

4. Run the app
```bash
npm start
```

Frontend will run at: `http://localhost:3000`

---

## Notes
- The backend uses H2 in-memory DB — data resets on restart
- CORS is not enabled by default; configure it in `WebConfig` if needed
- Handle `/favicon.ico` to prevent Spring mapping issues

---

## License
This project is for the HMCTS DTS Developer Challenge only.

---

Feel free to customize this project to showcase your skills! 🚀

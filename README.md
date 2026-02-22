# PixelForge Nexus - Secure Project Management Web Application

A complete, production-ready Spring Boot web application with advanced security features, role-based access control, and project management capabilities.

## Features

✨ **Authentication & Security**
- JWT-based authentication with Spring Security
- Role-Based Access Control (RBAC): ADMIN, PROJECT_LEAD, DEVELOPER
- BCrypt password hashing (strength=12 per OWASP recommendations)
- Login attempt tracking and brute-force protection (5 attempts = 15 min lockout)
- Session security with HTTP-only cookies
- CSRF protection
- Secure logout with session invalidation

📊 **Project Management**
- Create, view, and manage projects
- Project status tracking (ACTIVE/COMPLETED)
- Deadline management with date validation
- Project member assignment
- Team collaboration features

📁 **Document Management**
- Secure document upload with file type validation
- Allowed file types: PDF, DOC, DOCX, TXT, PNG, JPG
- File storage with UUID-based naming
- Access control verification before download
- Original filename preservation

👥 **User Management**
- User creation and role assignment
- Admin panel for user management
- Role modification capabilities
- Password change functionality
- Secure password management

🎨 **User Interface**
- Professional dark-themed design
- Responsive layout using CSS Grid
- Modern animations and transitions
- Inline styles for performance
- Flash messages for user feedback

## Technology Stack

- **Framework**: Spring Boot 3.3.0
- **Language**: Java 17
- **Database**: MongoDB
- **Template Engine**: Thymeleaf with Spring Security extras
- **Security**: Spring Security 6
- **Password Encoding**: BCrypt
- **Build Tool**: Maven
- **ORM**: Spring Data MongoDB

## Project Structure

```
PixelForge Nexus/
├── src/
│   ├── main/
│   │   ├── java/com/pixelforge/nexus/
│   │   │   ├── config/
│   │   │   │   └── DataSeeder.java
│   │   │   ├── controller/
│   │   │   │   ├── AccountController.java
│   │   │   │   ├── AdminController.java
│   │   │   │   ├── DashboardController.java
│   │   │   │   ├── FileController.java
│   │   │   │   ├── LoginController.java
│   │   │   │   └── ProjectController.java
│   │   │   ├── dto/
│   │   │   │   ├── ChangePasswordDTO.java
│   │   │   │   ├── CreateProjectDTO.java
│   │   │   │   └── CreateUserDTO.java
│   │   │   ├── model/
│   │   │   │   ├── Project.java
│   │   │   │   ├── ProjectMember.java
│   │   │   │   ├── ProjectStatus.java
│   │   │   │   ├── UploadedDocument.java
│   │   │   │   ├── User.java
│   │   │   │   └── UserRole.java
│   │   │   ├── repository/
│   │   │   │   ├── DocumentRepository.java
│   │   │   │   ├── ProjectMemberRepository.java
│   │   │   │   ├── ProjectRepository.java
│   │   │   │   └── UserRepository.java
│   │   │   ├── security/
│   │   │   │   ├── CustomUserDetailsService.java
│   │   │   │   ├── LoginAttemptService.java
│   │   │   │   └── SecurityConfig.java
│   │   │   ├── service/
│   │   │   │   ├── DocumentService.java
│   │   │   │   ├── ProjectService.java
│   │   │   │   └── UserService.java
│   │   │   └── PixelForgeNexusApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── templates/
│   │           ├── account.html
│   │           ├── dashboard.html
│   │           ├── login.html
│   │           ├── admin/users.html
│   │           ├── fragments/layout.html
│   │           └── projects/
│   │               ├── detail.html
│   │               └── list.html
│   └── test/
│       └── java/com/pixelforge/nexus/
│           └── PixelForgeNexusApplicationTests.java
├── pom.xml
└── README.md
```

## Prerequisites

- **Java**: JDK 17 or higher
- **Maven**: 3.8.0 or higher
- **MongoDB**: 5.0 or higher running on localhost:27017
- **Browser**: Modern browser with JavaScript enabled

## Setup Instructions

### 1. MongoDB Setup

Ensure MongoDB is running locally on port 27017:

```bash
# Using MongoDB Community Edition
mongod

# Or if using MongoDB Atlas, update connection string in application.properties
```

### 2. Build the Application

```bash
cd PixelForge
mvn clean package -DskipTests
```

### 3. Run the Application

```bash
java -jar target/nexus-1.0.0.jar
```

Or using Maven:

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### 4. Access the Application

Navigate to `http://localhost:8080/login`

**Demo Credentials** (automatically seeded on first run):

| Username | Password | Role |
|----------|----------|------|
| admin | Admin123! | ADMIN |
| lead1 | Lead123! | PROJECT_LEAD |
| dev1 | Dev123! | DEVELOPER |

## Application Configuration

### application.properties

```properties
# MongoDB Configuration
spring.data.mongodb.uri=mongodb://localhost:27017/pixelforge

# File Upload Configuration
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB

# Server Configuration
server.port=8080

# Session Security
server.servlet.session.cookie.http-only=true
server.servlet.session.cookie.same-site=strict

# Thymeleaf Configuration
spring.thymeleaf.cache=false
spring.thymeleaf.check-template-location=true
```

## Security Features Explained

### Authentication Flow

1. User submits credentials at `/login`
2. Spring Security validates against database
3. CustomUserDetailsService loads user with roles
4. BCryptPasswordEncoder verifies password
5. Session created with secure HTTP-only cookie
6. SAME_SITE=STRICT prevents CSRF attacks

### Role-Based Access Control

**ADMIN**
- Access all endpoints
- Create and complete projects
- Manage all users
- Assign project members
- Upload documents

**PROJECT_LEAD**
- View all projects
- Assign project members
- Upload documents
- Change own password

**DEVELOPER**
- View assigned projects only
- Download documents from assigned projects
- Change own password

### Brute Force Protection

- LoginAttemptService tracks failed login attempts per IP
- After 5 failed attempts: Account locked for 15 minutes
- Automatic unlock after lockout period expires

### Password Security

- Minimum 8 characters required
- Stored with BCrypt (strength=12)
- Salting prevents rainbow table attacks
- User cannot reuse old passwords

## Key Features Documentation

### Project Management

**Create Project** (ADMIN only)
- Name, description, deadline (future date required)
- Automatic status = ACTIVE
- Creator stored in database

**Assign Members** (ADMIN/PROJECT_LEAD)
- Prevent duplicate assignments
- Track assignment date and who assigned
- Developers see only assigned projects

**Complete Project** (ADMIN only)
- Change status from ACTIVE to COMPLETED
- Historical tracking maintained

### Document Management

**File Upload Restrictions**
- Allowed: PDF, DOC, DOCX, TXT, PNG, JPG
- Max file size: 10MB
- Stored in `./uploads/` directory
- Filename randomized with UUID
- Original filename preserved in database

**Access Control**
- ADMIN/PROJECT_LEAD: Access all documents
- DEVELOPER: Only documents from assigned projects
- Authentication required to download

### User Management

**Create User** (ADMIN only)
- Set username, email, password, role
- Email validation enforced
- Password encrypted immediately

**Modify Role** (ADMIN only)
- Change user role on-the-fly
- Takes effect on next login

**Change Password** (All users)
- Must verify current password
- Minimum 8 characters for new password
- Confirmation required

## API Endpoints

### Public Endpoints
- `GET /login` - Login page
- `POST /login` - Authenticate user

### Dashboard
- `GET /dashboard` - User dashboard

### Projects
- `GET /projects` - List all projects
- `GET /projects/{id}` - View project details
- `POST /projects/create` - Create project (ADMIN)
- `POST /projects/{id}/complete` - Complete project (ADMIN)
- `POST /projects/{id}/assign` - Assign member (ADMIN/LEAD)
- `POST /projects/{id}/upload` - Upload document (ADMIN/LEAD)

### User Management
- `GET /admin/users` - List all users (ADMIN)
- `POST /admin/users/create` - Create user (ADMIN)
- `POST /admin/users/{id}/role` - Update role (ADMIN)

### Account
- `GET /account` - Account settings
- `POST /account/password` - Change password

### Files
- `GET /uploads/{filename}` - Download document

## Troubleshooting

### MongoDB Connection Failed
```
Error: connect ECONNREFUSED 127.0.0.1:27017
```
**Solution**: Ensure MongoDB is running on port 27017
```bash
mongod --dbpath /path/to/db
```

### Port Already in Use
```
Error: Address already in use
```
**Solution**: Change port in application.properties
```properties
server.port=8081
```

### Lombok Not Working
**Solution**: Ensure IDE has Lombok plugin installed
- IntelliJ: Settings → Plugins → Search "Lombok" → Install
- Eclipse: Download lombok.jar and run `java -jar lombok.jar`

### Files Not Uploading
**Solution**: Ensure `./uploads/` directory exists and is writable
```bash
mkdir uploads
chmod 755 uploads
```

## Development Notes

### Adding New Features

1. **Create Entity Model** → `src/main/java/com/pixelforge/nexus/model/`
2. **Create Repository** → `src/main/java/com/pixelforge/nexus/repository/`
3. **Create DTO** → `src/main/java/com/pixelforge/nexus/dto/`
4. **Create Service** → `src/main/java/com/pixelforge/nexus/service/`
5. **Create Controller** → `src/main/java/com/pixelforge/nexus/controller/`
6. **Create Template** → `src/main/resources/templates/`

### Code Standards

- Use Lombok annotations (@Getter, @Setter, @Builder)
- Validate input with Jakarta validation annotations
- Use try-catch in controllers with flash messages
- Follow Spring naming conventions
- Add security comments explaining WHY (not just WHAT)

### Testing

Run unit tests:
```bash
mvn test
```

Run integration tests:
```bash
mvn verify
```

## Deployment

### Docker
```dockerfile
FROM openjdk:17-slim
COPY target/nexus-1.0.0.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### Build Docker Image
```bash
docker build -t pixelforge-nexus:1.0.0 .
```

### Run Container
```bash
docker run -p 8080:8080 \
  -e SPRING_DATA_MONGODB_URI=mongodb://mongo:27017/pixelforge \
  pixelforge-nexus:1.0.0
```

## Security Checklist for Production

- [ ] Change default passwords
- [ ] Enable HTTPS with SSL certificates
- [ ] Set strong database authentication
- [ ] Configure firewall rules
- [ ] Enable rate limiting
- [ ] Set up monitoring and logging
- [ ] Regular backups of MongoDB
- [ ] Implement API rate limiting
- [ ] Use environment variables for secrets
- [ ] Enable CORS only for trusted domains

## License

This project is provided as-is for educational and demonstration purposes.

## Support

For issues or questions:
1. Check the troubleshooting section
2. Review Spring Boot documentation
3. Check MongoDB logs for database issues
4. Verify all dependencies are correctly installed

---

**PixelForge Nexus v1.0.0** - Built with Spring Boot 3.3.0


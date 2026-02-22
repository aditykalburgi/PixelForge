# PixelForge Nexus - Complete Application Guide

## 📚 Documentation Index

Welcome to **PixelForge Nexus** - A complete, production-ready secure web application built with Spring Boot 3.3.0, Java 17, and MongoDB.

---

## 🚀 Getting Started (Choose Your Path)

### ⏱️ **5 Minutes** - Quick Start
👉 Read: `QUICK_START.md`
- Install prerequisites
- Start MongoDB
- Run the JAR file
- Try demo accounts

### 📖 **30 Minutes** - Full Setup & Deployment
👉 Read: `DEPLOYMENT.md`
- Detailed running options
- Configuration settings
- Troubleshooting guide
- Security reminders

### 📚 **Complete Reference** - Full Documentation
👉 Read: `README.md`
- Feature details
- Architecture explanation
- API endpoints
- Development notes
- Production deployment

### 📋 **Technical Overview** - What Was Built
👉 Read: `BUILD_SUMMARY.md`
- Project statistics
- File structure
- Technology stack
- Security implementation details

---

## ✨ Key Features

### 🔐 Security
```
✅ Spring Security 6 with role-based access control
✅ BCrypt password hashing (strength=12, OWASP compliant)
✅ Brute force protection (5 attempts = 15 min lockout)
✅ Secure session management with HTTP-only cookies
✅ CSRF protection on all forms
✅ File upload validation (whitelist)
✅ Access control verification on file download
```

### 👥 User Management
```
✅ Three roles: ADMIN, PROJECT_LEAD, DEVELOPER
✅ Create users (ADMIN only)
✅ Modify user roles (ADMIN only)
✅ Change password (all users)
✅ Secure authentication
✅ Login attempt tracking
```

### 📊 Project Management
```
✅ Create projects (ADMIN only)
✅ Assign team members (ADMIN/LEAD)
✅ Track project status (ACTIVE/COMPLETED)
✅ Deadline management
✅ View project details
✅ Role-based project visibility
```

### 📁 Document Management
```
✅ Upload documents (ADMIN/LEAD)
✅ File type validation (PDF, DOC, DOCX, TXT, PNG, JPG)
✅ Secure file storage with UUID naming
✅ Download with access verification
✅ Original filename preservation
✅ Role-based access control
```

### 🎨 User Interface
```
✅ Professional dark theme
✅ Responsive design with CSS Grid
✅ Smooth animations and transitions
✅ Flash message notifications
✅ Modal forms
✅ Real-time validation
✅ Status badges and indicators
```

---

## 📦 What's Included

### Java Classes (27 files)
```
✅ 6 Entity Models (User, Project, ProjectMember, Document, Enums)
✅ 4 MongoDB Repositories
✅ 3 Business Logic Services
✅ 6 REST Controllers
✅ 3 Security Components
✅ 3 Data Transfer Objects (DTOs)
✅ 1 Configuration & Data Seeder
✅ 1 Main Application Class
```

### HTML Templates (7 files)
```
✅ login.html              - Professional login page
✅ dashboard.html          - User dashboard with stats
✅ projects/list.html      - Projects listing table
✅ projects/detail.html    - Project details with features
✅ admin/users.html        - User management panel
✅ account.html            - Account settings
✅ fragments/layout.html   - Shared layout
```

### Configuration & Documentation
```
✅ pom.xml                 - Maven dependencies
✅ application.properties  - Spring Boot configuration
✅ README.md              - Complete documentation (2000+ lines)
✅ QUICK_START.md         - 5-minute quick start
✅ DEPLOYMENT.md          - Deployment guide
✅ BUILD_SUMMARY.md       - Technical overview
✅ INDEX.md               - This file
```

---

## 🎯 Quick Links by Use Case

### "I want to run the app NOW"
```
1. Ensure MongoDB is running: mongod
2. Execute: java -jar target/nexus-1.0.0.jar
3. Open: http://localhost:8080/login
4. Login: admin / Admin123!
```
📖 Full details: `DEPLOYMENT.md`

### "I want to learn what was built"
👉 Read: `BUILD_SUMMARY.md` (5 min)
Then: `README.md` Technical Stack section (10 min)

### "I need to customize/extend the app"
👉 Read: `README.md` Development Notes section
Then: Review code in `src/main/java/com/pixelforge/nexus/`

### "I want to deploy to production"
👉 Read: `README.md` Deployment section
Then: `DEPLOYMENT.md` Security checklist

### "I'm having issues"
👉 Check: `DEPLOYMENT.md` Troubleshooting section
If not found: `README.md` Troubleshooting section

---

## 🔐 Security Overview

### Authentication
- Form-based login with Spring Security
- Custom UserDetailsService
- Role-based authorities

### Password Protection
- BCrypt with strength=12
- 8 character minimum
- Validation on all changes

### Brute Force Defense
- Track failed attempts per IP
- 5 attempts = 15 minute lockout
- Automatic unlock after period

### Session Security
- HTTP-only cookies
- SAME_SITE=STRICT
- Secure logout
- Session invalidation

### File Security
- Whitelist validation
- UUID-based storage
- Access control checks
- Role-based permissions

### CSRF Protection
- Built into Spring Security
- Automatic token in forms
- Thymeleaf integration

---

## 📊 Project Statistics

```
Total Java Classes:     27
HTML Templates:         7
Configuration Files:    3
Total Code Lines:       5,000+
Compiled JAR Size:      ~60MB
Dependencies:           11 Spring Boot starters

Build Status: ✅ SUCCESS
Compilation: ✅ ERROR-FREE
Ready to Run: ✅ YES
```

---

## 🗂️ Directory Map

```
PixelForge/
│
├── 📄 DOCUMENTATION (Read these first)
│   ├── INDEX.md ......................... 👈 START HERE
│   ├── QUICK_START.md .................. 5 minute setup
│   ├── DEPLOYMENT.md ................... How to run
│   ├── BUILD_SUMMARY.md ................ Technical details
│   └── README.md ....................... Complete reference
│
├── 🔧 BUILD & CONFIG
│   ├── pom.xml ......................... Maven dependencies
│   ├── target/
│   │   └── nexus-1.0.0.jar ............ 🚀 RUN THIS
│   └── mvnw, mvnw.cmd .................. Build scripts
│
├── 💻 SOURCE CODE
│   └── src/main/java/com/pixelforge/nexus/
│       ├── PixelForgeNexusApplication.java .... Main class
│       ├── config/ ..................... Configuration
│       ├── controller/ ................. REST endpoints
│       ├── model/ ...................... Data entities
│       ├── repository/ ................. Data access
│       ├── security/ ................... Auth & security
│       ├── service/ .................... Business logic
│       └── dto/ ........................ Request validation
│
├── 🎨 TEMPLATES
│   └── src/main/resources/templates/
│       ├── login.html .................. Login page
│       ├── dashboard.html .............. Dashboard
│       ├── account.html ................ Account settings
│       ├── admin/
│       │   └── users.html ............. User management
│       ├── projects/
│       │   ├── list.html .............. Projects list
│       │   └── detail.html ............ Project details
│       └── fragments/
│           └── layout.html ............ Shared layout
│
├── ⚙️ CONFIGURATION
│   └── src/main/resources/
│       └── application.properties ..... Spring config
│
└── 📁 RUNTIME
    └── uploads/ ....................... Document storage
```

---

## 🔑 Demo Credentials

Automatically seeded on first run:

```
┌──────────┬─────────────┬──────────────┬─────────────────────────────┐
│ Username │  Password   │ Role         │ Capabilities                │
├──────────┼─────────────┼──────────────┼─────────────────────────────┤
│ admin    │ Admin123!   │ ADMIN        │ Full access, manage all     │
│ lead1    │ Lead123!    │ PROJECT_LEAD │ Create projects, assign     │
│ dev1     │ Dev123!     │ DEVELOPER    │ View projects, download     │
└──────────┴─────────────┴──────────────┴─────────────────────────────┘
```

---

## 🚀 Run Commands

### Basic Run
```bash
cd C:\Users\adity\OneDrive\Desktop\PixelForge
java -jar target/nexus-1.0.0.jar
```

### Custom Port
```bash
java -jar target/nexus-1.0.0.jar --server.port=9090
```

### Custom MongoDB
```bash
java -jar target/nexus-1.0.0.jar \
  --spring.data.mongodb.uri=mongodb://localhost:27017/pixelforge
```

### From IDE/Maven
```bash
mvn spring-boot:run
```

---

## 📱 Browser Access

After starting the application:

| Purpose | URL |
|---------|-----|
| Login | http://localhost:8080/login |
| Dashboard | http://localhost:8080/dashboard |
| Projects | http://localhost:8080/projects |
| Users (Admin) | http://localhost:8080/admin/users |
| Account | http://localhost:8080/account |

---

## 🛠️ Technology Stack

| Layer | Technology | Version |
|-------|-----------|---------|
| **Framework** | Spring Boot | 3.3.0 |
| **Language** | Java | 17 |
| **Database** | MongoDB | Any recent |
| **Web** | Spring Web | 3.3.0 |
| **Security** | Spring Security | 6.x |
| **Data Access** | Spring Data MongoDB | 3.3.0 |
| **Templates** | Thymeleaf | 3.x |
| **ORM** | Lombok | Latest |
| **Validation** | Jakarta Validation | Latest |
| **Build** | Maven | 3.8.0+ |

---

## ✅ Pre-Build Checklist

Before running, ensure you have:

```
✅ Java 17 or higher installed
✅ Maven 3.8.0 or higher installed
✅ MongoDB running on localhost:27017
✅ Port 8080 available (or configure different)
✅ Internet connection (for first run, dependencies)
✅ ~200MB disk space for dependencies
```

---

## 🎓 Learning Path

### Beginner (Just Want to Use It)
1. Start here: `QUICK_START.md`
2. Run the application
3. Try demo accounts
4. Explore UI

### Intermediate (Want to Understand)
1. Read: `BUILD_SUMMARY.md`
2. Review: `README.md` sections on features
3. Explore code structure
4. Try customizations

### Advanced (Want to Extend)
1. Study: `README.md` Development Notes
2. Review: Security implementation
3. Understand: Database schema
4. Add features: Following patterns

### Production (Want to Deploy)
1. Read: `README.md` Deployment section
2. Review: Security checklist
3. Prepare: Environment configuration
4. Deploy: Using Docker or VPS

---

## 📞 Need Help?

### Quick Problems?
👉 Check: `DEPLOYMENT.md` - Troubleshooting section

### General Questions?
👉 Read: `README.md` - Complete documentation

### Want to Build Something?
👉 Read: `README.md` - Development Notes section

### Production Deployment?
👉 Read: `README.md` - Deployment section

---

## 🎉 Ready to Start?

```
┌─────────────────────────────────────────────────────────────┐
│                                                              │
│  ✨ PixelForge Nexus v1.0.0                                 │
│  🔒 Secure Project Management Application                   │
│  ⚡ Ready to Run                                             │
│                                                              │
│  Next Step:                                                 │
│  1. Open: QUICK_START.md                                   │
│  2. OR Run: java -jar target/nexus-1.0.0.jar              │
│  3. Visit: http://localhost:8080/login                     │
│                                                              │
│  Questions? See: README.md                                 │
│                                                              │
└─────────────────────────────────────────────────────────────┘
```

---

## 📖 Documentation Files

| File | Purpose | Read Time |
|------|---------|-----------|
| **INDEX.md** | Navigation guide | 5 min |
| **QUICK_START.md** | 5-minute setup | 5 min |
| **DEPLOYMENT.md** | How to run & troubleshoot | 15 min |
| **BUILD_SUMMARY.md** | What was built | 10 min |
| **README.md** | Complete reference | 30 min |

---

**PixelForge Nexus** | Built with ❤️ using Spring Boot | v1.0.0 | February 2026


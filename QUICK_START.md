# PixelForge Nexus - Quick Start Guide

## 🚀 Get Started in 5 Minutes

### Prerequisites Check
```bash
# Check Java version (must be 17+)
java -version

# Check Maven version (must be 3.8.0+)
mvn -version

# Check MongoDB is running
mongo --eval "db.adminCommand('ping')"
```

### Step 1: Start MongoDB
```bash
# On Windows
mongod

# On macOS/Linux
brew services start mongodb-community
# or
mongod --dbpath /usr/local/var/mongodb
```

### Step 2: Build & Run
```bash
cd C:\Users\adity\OneDrive\Desktop\PixelForge
mvn clean package -DskipTests
java -jar target/nexus-1.0.0.jar
```

### Step 3: Open Browser
Navigate to: `http://localhost:8080/login`

### Step 4: Login with Demo Account
```
Username: admin
Password: Admin123!
```

## 📋 First Time Setup

The application automatically:
1. ✅ Creates 3 demo users (admin, lead1, dev1)
2. ✅ Creates uploads directory
3. ✅ Initializes MongoDB connection
4. ✅ Sets up security configuration

## 👤 Demo Users

### Admin Account
- **Username**: admin
- **Password**: Admin123!
- **Permissions**: Full access, manage everything

### Project Lead Account
- **Username**: lead1
- **Password**: Lead123!
- **Permissions**: Create projects, assign members, upload docs

### Developer Account
- **Username**: dev1
- **Password**: Dev123!
- **Permissions**: View assigned projects, download docs

## 🎯 Try These Actions

### As Admin:
1. Login with admin credentials
2. Go to "Users" → Create new user
3. Go to "Projects" → Create new project
4. Assign team members to project
5. View all projects and users

### As Project Lead:
1. Login with lead1 credentials
2. View all projects
3. Assign developers to projects
4. Upload documents
5. Change password

### As Developer:
1. Login with dev1 credentials
2. View only assigned projects
3. Download documents from projects
4. Change password
5. View account settings

## 🛠️ Common Tasks

### Create a New Project
1. Login as Admin
2. Click "+ New Project"
3. Enter name, description, deadline
4. Click "Create Project"
5. Add members and documents

### Assign User to Project
1. Go to project detail page
2. Scroll to "Assign Member"
3. Select user from dropdown
4. Click "Assign Member"

### Upload Document
1. Go to project detail page
2. Scroll to "Upload Document"
3. Choose file (PDF, DOC, DOCX, TXT, PNG, JPG)
4. Click "Upload"
5. Download via document link

### Change Password
1. Click "Account" in navbar
2. Enter current password
3. Enter new password (min 8 chars)
4. Confirm new password
5. Click "Update Password"

## 📁 Project Structure

```
PixelForge/
├── target/                    # Compiled application
│   └── nexus-1.0.0.jar       # Runnable JAR file
├── uploads/                   # Uploaded documents storage
├── src/
│   ├── main/java/            # Source code
│   ├── main/resources/        # Templates & config
│   └── test/                  # Test code
├── pom.xml                    # Maven configuration
├── README.md                  # Full documentation
└── QUICK_START.md            # This file
```

## 🔒 Security Features

✅ **Password Security**
- BCrypt encryption (strength=12)
- Minimum 8 characters
- Never stored in plain text

✅ **Login Protection**
- Brute force defense (5 attempts = 15 min lock)
- Failed attempt tracking per IP
- Automatic unlock after timeout

✅ **Access Control**
- Role-based permissions (ADMIN/LEAD/DEV)
- Session-based authentication
- HTTP-only secure cookies

✅ **File Security**
- File type validation (no .exe, .sh, etc.)
- Access verification before download
- UUID-based storage names

## 🐛 Troubleshooting

### Application won't start
```
Error: Cannot connect to MongoDB
Solution: 
  1. Check MongoDB is running
  2. Verify connection string in application.properties
  3. Check port 27017 is not blocked
```

### Port 8080 already in use
```
Error: Address already in use: bind
Solution:
  1. Change port: server.port=8081 in application.properties
  2. Or kill process using port 8080
```

### File upload fails
```
Error: IOException during file upload
Solution:
  1. Check ./uploads/ directory exists
  2. Ensure file size < 10MB
  3. Verify file type in whitelist
```

### Can't login after creating user
```
Solution:
  1. Clear browser cookies
  2. Check password was entered correctly
  3. Verify user email is unique
```

## 📊 Database Collections

Created automatically in MongoDB:

- `users` - Stores user accounts and credentials
- `projects` - Stores project information
- `project_members` - Maps users to projects
- `uploaded_documents` - Tracks uploaded files

## 🔗 Useful URLs

| URL | Description |
|-----|-------------|
| http://localhost:8080/login | Login page |
| http://localhost:8080/dashboard | User dashboard |
| http://localhost:8080/projects | All projects |
| http://localhost:8080/admin/users | User management |
| http://localhost:8080/account | Account settings |

## 📞 Need Help?

### Check Logs
```bash
# If running with:
java -jar target/nexus-1.0.0.jar

# Logs appear in console
# Look for [ERROR] or [WARN] messages
```

### Verify Dependencies
```bash
mvn dependency:tree
```

### Validate Configuration
```bash
# Check if MongoDB is accessible
mongo --host localhost --port 27017
```

## 🚀 Next Steps

1. ✅ Get application running
2. ✅ Try demo users
3. ✅ Create projects and assign members
4. ✅ Upload and download documents
5. 📖 Read full README.md for advanced features
6. 🔐 Review security configuration
7. 🎨 Customize templates in src/main/resources/templates/
8. 💼 Deploy to production (see README for Docker setup)

## 📝 Default Configuration

```
Database: MongoDB at localhost:27017/pixelforge
Server Port: 8080
Max Upload: 10MB
Session Security: HTTP-only, SAME_SITE=STRICT
Password Encoding: BCrypt (strength=12)
Brute Force Limit: 5 attempts per 15 minutes
```

---

**✨ PixelForge Nexus is now ready to use!**

For detailed documentation, see `README.md`


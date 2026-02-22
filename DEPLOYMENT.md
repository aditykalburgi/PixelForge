# PixelForge Nexus - Deployment & Execution Guide

## 🎯 Ready to Deploy

Your PixelForge Nexus application is fully compiled and ready to run!

**Compiled JAR**: `C:\Users\adity\OneDrive\Desktop\PixelForge\target\nexus-1.0.0.jar`

---

## ⚡ Quick Start (30 seconds)

### Prerequisites
```bash
# Ensure MongoDB is running on localhost:27017
mongod
```

### Run the Application
```bash
# Navigate to project directory
cd C:\Users\adity\OneDrive\Desktop\PixelForge

# Execute the JAR
java -jar target/nexus-1.0.0.jar
```

### Access the Application
```
Open browser: http://localhost:8080/login

Login credentials:
  Username: admin
  Password: Admin123!
```

---

## 📋 Running Options

### Option 1: Direct JAR Execution (Recommended for Development)
```bash
cd C:\Users\adity\OneDrive\Desktop\PixelForge
java -jar target/nexus-1.0.0.jar
```

### Option 2: Maven Spring Boot Plugin
```bash
cd C:\Users\adity\OneDrive\Desktop\PixelForge
mvn spring-boot:run
```

### Option 3: Docker (Production)
```bash
# Build Docker image
docker build -t pixelforge-nexus:1.0.0 .

# Run container
docker run -p 8080:8080 \
  -e SPRING_DATA_MONGODB_URI=mongodb://mongo:27017/pixelforge \
  pixelforge-nexus:1.0.0
```

### Option 4: Custom Configuration
```bash
java -jar target/nexus-1.0.0.jar \
  --server.port=8081 \
  --spring.data.mongodb.uri=mongodb://localhost:27017/pixelforge
```

---

## 🔧 Configuration Options

### Port Change
```bash
java -jar target/nexus-1.0.0.jar --server.port=9090
```

### MongoDB Connection
```bash
java -jar target/nexus-1.0.0.jar \
  --spring.data.mongodb.uri=mongodb://username:password@host:27017/pixelforge
```

### Environment Variables
```bash
set SPRING_DATA_MONGODB_URI=mongodb://localhost:27017/pixelforge
set SERVER_PORT=8080
java -jar target/nexus-1.0.0.jar
```

---

## 🗂️ Directory Structure After Running

```
PixelForge/
├── target/
│   └── nexus-1.0.0.jar       ← Run this file
├── uploads/                  ← Created on first run
│   └── [uploaded documents stored here]
├── application.properties
├── pom.xml
├── README.md
├── QUICK_START.md
└── BUILD_SUMMARY.md
```

---

## ✅ Verification Checklist

After starting the application, verify:

- [ ] No errors in console logs
- [ ] See "Started PixelForgeNexusApplication" message
- [ ] Server listening on http://localhost:8080
- [ ] MongoDB connection successful
- [ ] "DataSeeder: Initial users created successfully" in logs

### Example Successful Startup Logs
```
2026-02-22 12:00:00.000  INFO 12345 --- [...] PixelForgeNexusApplication : Starting PixelForgeNexusApplication
2026-02-22 12:00:02.123  INFO 12345 --- [...] DataSeeder : DataSeeder: Initial users created successfully
2026-02-22 12:00:05.456  INFO 12345 --- [...] TomcatWebServer : Tomcat started on port(s): 8080 (http)
```

---

## 🔑 Default Login Credentials

These users are automatically created on first run:

### Admin Account
```
Username: admin
Password: Admin123!
Role: ADMIN (Full access)
```

### Project Lead Account
```
Username: lead1
Password: Lead123!
Role: PROJECT_LEAD (Can manage projects)
```

### Developer Account
```
Username: dev1
Password: Dev123!
Role: DEVELOPER (Can view assigned projects)
```

---

## 🚪 Application Endpoints

| Endpoint | Type | Auth | Description |
|----------|------|------|-------------|
| /login | GET | Public | Login page |
| /dashboard | GET | Required | User dashboard |
| /projects | GET | Required | All projects |
| /projects/{id} | GET | Required | Project details |
| /admin/users | GET | ADMIN | User management |
| /account | GET | Required | Account settings |
| /logout | POST | Required | Logout |

---

## 📊 Expected Directory Usage

First run will create:

```
./uploads/                    ← Document storage
  └── [uuid-filenames].pdf   ← Uploaded files
  └── [uuid-filenames].doc
  └── ...
```

MongoDB collections created:
```
- users                    ← User accounts
- projects                 ← Projects
- project_members          ← Team assignments
- uploaded_documents       ← Document metadata
```

---

## 🐛 Troubleshooting

### Error: "Address already in use: bind"
**Cause**: Port 8080 already in use
```bash
# Solution 1: Use different port
java -jar target/nexus-1.0.0.jar --server.port=8081

# Solution 2: Kill process on port 8080
netstat -ano | findstr :8080
taskkill /PID [PID] /F
```

### Error: "Cannot connect to MongoDB"
**Cause**: MongoDB not running
```bash
# Solution 1: Start MongoDB
mongod

# Solution 2: Check connection string
# Verify in application.properties:
# spring.data.mongodb.uri=mongodb://localhost:27017/pixelforge
```

### Error: "No such file or directory: ./uploads"
**Solution**: Directory is auto-created, but if missing:
```bash
mkdir uploads
chmod 755 uploads
```

### Application won't compile after changes
```bash
# Clean and rebuild
mvn clean compile
mvn package -DskipTests
```

---

## 🎯 First-Time Setup Steps

### Step 1: Start MongoDB
```bash
mongod --dbpath "C:\path\to\mongodb\data"
```
Wait for "waiting for connections on port 27017"

### Step 2: Run Application
```bash
cd C:\Users\adity\OneDrive\Desktop\PixelForge
java -jar target/nexus-1.0.0.jar
```
Wait for "Started PixelForgeNexusApplication"

### Step 3: Access Application
Open browser to: `http://localhost:8080/login`

### Step 4: Login as Admin
```
Username: admin
Password: Admin123!
```

### Step 5: Explore Features
1. Create a new project
2. Create a new user
3. Assign user to project
4. Upload a document
5. Test different user roles

---

## 📈 Performance Tuning

### Increase Memory (if needed)
```bash
java -Xmx2048m -Xms1024m -jar target/nexus-1.0.0.jar
```

### Enable Debug Logging
```bash
java -jar target/nexus-1.0.0.jar --debug
```

### Monitor Application
```bash
# Check JVM metrics
jps -l                                    # List Java processes
jstat -gc [PID] 1000 10                  # GC statistics
jconsole                                  # GUI monitoring
```

---

## 🔐 Security Reminders

### Before Production Deployment

- [ ] Change default passwords
- [ ] Enable HTTPS/SSL
- [ ] Set up firewall rules
- [ ] Configure MongoDB authentication
- [ ] Enable backup policies
- [ ] Set up monitoring
- [ ] Review logs regularly
- [ ] Use environment variables for secrets

### Security Configuration
```bash
# Set strong MongoDB password
mongo
> use admin
> db.createUser({user: "admin", pwd: "StrongPassword123!", roles: ["root"]})

# Connect with authentication
--spring.data.mongodb.uri=mongodb://admin:StrongPassword123!@localhost:27017/pixelforge?authSource=admin
```

---

## 📝 Logs & Debugging

### View Live Logs
```bash
# Already visible in console when running JAR
# Or in IDE if running from Spring Boot plugin
```

### Save Logs to File
```bash
java -jar target/nexus-1.0.0.jar > app.log 2>&1
```

### View Specific Logs
```bash
# Show only errors
cat app.log | grep ERROR

# Show startup sequence
cat app.log | grep -E "(Started|Initialized|Created)"
```

---

## 🚀 Deployment Checklist

### Local Development
- [x] Application compiles
- [x] JAR file created
- [x] MongoDB available
- [x] Can start without errors
- [x] Demo users seeded
- [x] Can login
- [x] UI renders correctly

### Pre-Production
- [ ] Database backups configured
- [ ] Monitoring set up
- [ ] Logging configured
- [ ] HTTPS enabled
- [ ] Passwords changed
- [ ] Database authentication enabled
- [ ] Firewall rules set

### Production Deployment
- [ ] Load balancer configured (if needed)
- [ ] Database replicas/shards set up
- [ ] SSL certificates installed
- [ ] CDN configured (if needed)
- [ ] Health checks enabled
- [ ] Alerting configured
- [ ] Disaster recovery plan ready

---

## 📞 Quick Reference

### Most Used Commands
```bash
# Build
mvn clean package -DskipTests

# Run
java -jar target/nexus-1.0.0.jar

# Run on different port
java -jar target/nexus-1.0.0.jar --server.port=9090

# Run with debug
java -jar target/nexus-1.0.0.jar --debug

# Run with MongoDB override
java -jar target/nexus-1.0.0.jar \
  --spring.data.mongodb.uri=mongodb://localhost:27017/pixelforge
```

### Browser URLs
```
Login:     http://localhost:8080/login
Dashboard: http://localhost:8080/dashboard
Projects:  http://localhost:8080/projects
Users:     http://localhost:8080/admin/users
Account:   http://localhost:8080/account
```

---

## ✨ You're All Set!

**Your PixelForge Nexus application is ready to run.**

```bash
# Execute this command to start:
java -jar target/nexus-1.0.0.jar

# Then open: http://localhost:8080/login
```

**Enjoy the secure project management experience!**

---

For detailed documentation, see:
- **README.md** - Complete feature documentation
- **QUICK_START.md** - 5-minute setup guide  
- **BUILD_SUMMARY.md** - Technical overview


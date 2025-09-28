# Kaiburr-Task1

# Kaiburr Task Manager REST API

This project provides complete CRUD operations with command validation and execution tracking.

## 🚀 Project Overview

This application allows you to:
- Create, read, update, and delete task objects
- Execute shell commands safely with validation
- Search tasks by name or retrieve by ID
- Store data persistently using mongoDb integration

## 📋 Prerequisites

Before you begin, ensure you have the following installed:

- **Java JDK 17 or higher** 
  - Download from [Oracle](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://adoptium.net/)
  - Set `JAVA_HOME` environment variable
- **Apache Maven 3.6+**
  - Download from [Apache Maven](https://maven.apache.org/download.cgi)
  - Add `mvn` to your system PATH
- **Postman** (for API testing)
  - Download from [Postman](https://www.postman.com/downloads/)
- **VS Code or any IDE** (optional but recommended)

### Verify Installation

Open terminal/command prompt and run:
```bash
java -version
mvn -version
git --version
```


## 🛠️ Setup Instructions

### Step 1: Create Project Directory

```bash
# Create a new directory for your project
mkdir taskmanager
cd taskmanager
```

### Step 2: Create Maven Project Structure

```bash
# Create the required directory structure
mkdir -p src/main/java/com/kaiburr/taskmanager/{controller,model,repository,service}
mkdir -p src/main/resources
mkdir -p src/test/java
```


### Step 3: Create Java Source Files

You'll need to create the following Java files with the complete code:

1. **TaskManagerApplication.java** - Main Spring Boot application
2. **Task.java** - Entity class for tasks
3. **TaskExecution.java** - Embedded class for execution details
4. **TaskRepository.java** - Data repository interface
5. **CommandValidator.java** - Service for command safety validation
6. **TaskController.java** - REST controller with all endpoints


## 🚀 Running the Application

### Step 1: Build the Project

```bash
# Clean and install dependencies
mvn clean install
```

### Step 2: Run the Application

```bash
# Start the Spring Boot application
mvn spring-boot:run
```


### Step 3: Verify Application is Running

Open browser and navigate to: `http://localhost:8080/tasks`

You should see either an empty array `[]` or a JSON response (not a 404 error).

## 🧪 Testing with Postman

### Setting Up Postman Collection

1. **Open Postman**
2. **Create New Collection** called "Kaiburr Task Manager API"
3. **Add requests as described below**

Method	Endpoint	Description
GET	/tasks	Get all tasks
GET	/tasks?id={id}	Get task by ID
PUT	/tasks	Create or update task
DELETE	/tasks?id={id}	Delete task by ID
GET	/tasks/search?name={name}	Search tasks by name
PUT	/tasks/{id}/execute	Execute task command
Sample Requests



### Common Issues and Solutions

#### 1. "mvn command not found"
**Problem:** Maven is not installed or not in PATH
**Solution:**
- Download and install Maven from official site
- Add Maven bin directory to PATH environment variable
- Restart terminal and try again

#### 2. Application fails to start
**Problem:** Port 8080 is already in use
**Solution:**
```bash
# Check what's using port 8080
netstat -ano | findstr :8080

# Kill the process or change port in application.properties
server.port=8081
```

#### 3. "404 Not Found" for all endpoints
**Problem:** Controller mapping issues
**Solution:**
- Verify all Java files are in correct packages
- Check that @RestController and @RequestMapping annotations are present
- Restart application after code changes

### Debug Steps

1. **Check application logs** for error messages
2. **Verify Java and Maven versions** meet requirements
3. **Test with simple curl commands** before using Postman
4. **Check firewall/antivirus** isn't blocking port 8080


## 🔒 Security Features

- **Command Validation:** Blocks dangerous commands like `rm`, `sudo`, `shutdown`
- **Input Sanitization:** Validates all user inputs
- **Error Handling:** Proper HTTP status codes and error messages
- **Safe Execution:** Commands run in isolated processes with timeouts



---

**Happy Coding! 🎉**

*This project demonstrates a complete REST API implementation using Spring Boot with proper error handling, validation, and documentation.*

Developed By :
Pragadheesh RA
pragadheesharumugam@gmail.com / 126003197@sastra.ac.in

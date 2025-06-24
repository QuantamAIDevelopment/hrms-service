# Human Resource Management System (HRMS) - Project Overview

## 1. Project Introduction
- **Project Name**: Human Resource Management System (HRMS)
- **Type**: Enterprise-level HR Management Solution
- **Technology Stack**: Java-based Spring Boot Application
- **Architecture**: Microservices-based architecture with Azure Cloud Integration

## 2. Technical Stack
### Backend Technologies
- **Core Framework**: Spring Boot 3.4.6
- **Language**: Java 17
- **Database**: PostgreSQL (Azure Database)
- **Security**: Spring Security with JWT Authentication
- **API Documentation**: SpringDoc OpenAPI (Swagger)

### Cloud Services (Azure Integration)
- Azure Communication Services (Email)
- Azure Storage (Blob & File Share)
- Azure Key Vault
- Azure Active Directory
- Azure Application Insights
- Azure Container Apps

## 3. Modules Overview

### 1. Employees
Manage employee profiles, information, and records. Includes CRUD operations, department assignment, supervisor management, and employment status tracking.

### 2. Vibe
Track company culture and engagement. Allows creation and management of company vibe entries, feedback, and engagement metrics.

### 3. Attendance
Track employee attendance and time records. Supports check-in/check-out, attendance logs, and reporting by date or employee.

### 4. Leave
Handle employee leave requests and approvals. Includes leave type management, leave balance tracking, approval workflows, and leave statistics.

### 5. HR Policies
Access and manage HR policies and guidelines. Allows creation, update, and categorization of HR policies for employee reference.

### 6. HR Documents
Store and manage employee documents. Supports secure upload, versioning, expiry tracking, and access control for sensitive documents.

### 7. Calendar
View and manage company events. Enables scheduling, updating, and categorizing events, and viewing events by date or type.

### 8. Performance
Track and evaluate employee performance. Supports performance review creation, updates, and retrieval by employee or period.

### 9. Org View
View organizational structure and hierarchy. Manages departments, designations, reporting structure, and provides organization charts.

### 10. Flows
Manage workflow and processes. Allows creation and management of business process flows, status tracking, and workflow automation.

### 11. Time Sheets
Track and manage employee time sheets. Supports submission, approval, and reporting of time worked by employees.

### 12. Salary
Manage employee salaries and compensation. Handles salary structure, pay periods, approval workflows, and salary history.

### 13. Travel
Handle travel requests and expenses. Manages travel requests, approvals, expense tracking, and travel history.

### 14. Reports
Generate and view various reports. Supports custom report generation, export, scheduling, and access control.

### 15. 3D Demo
Showcase 3D visualizations or demos related to HR processes or organizational data.

## 4. Security & Authentication
- JWT-based authentication
- OAuth2 integration with Azure AD
- Role-based access control
- Secure key management through Azure Key Vault

## 5. Data Management & Compliance
- Enhanced compliance management
- Secure document and data storage
- Audit trails and reporting

## 6. User Roles
- Admin, HR, Manager, Employee
- Role-based access and permissions

## 7. API Documentation
- Swagger/OpenAPI for all endpoints

## 8. Deployment
- Azure Cloud deployment with containerization and monitoring

---

This project provides a comprehensive HRMS solution covering all major HR functions, with modular design for easy extension and integration.
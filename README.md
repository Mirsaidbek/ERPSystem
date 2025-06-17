# Enterprise Resource Planning (ERP) System

A comprehensive HR-focused Enterprise Resource Planning system that manages employee lifecycle, attendance, leave management, and document handling with role-based access control.

## System Overview

### User Roles and Responsibilities

#### 1. Administrator (ADMIN)
Administrators are the system's superusers with complete control over the platform:
- Create and manage HR personnel (users with USER role)
- Access and modify system-wide settings
- View and manage all employee records
- Override and manage access controls
- Initial admin credentials (auto-created on first run):
  - Username: admin@erp.com
  - Password: admin1234

#### 2. HR Personnel (USER)
HR users manage the day-to-day human resource operations:
- Create and manage employee accounts
- Process leave requests
- Monitor attendance records
- Manage employee documentation
- View employee profiles and performance data
- Generate HR reports

#### 3. Employees (EMPLOYEE)
Regular employees have access to self-service features:
- View and update their profile information
- Submit and track leave requests
- Record attendance (check-in/check-out)
- Upload and manage their resume
- View their employment history

### Core Features

#### Employee Lifecycle Management
- **Onboarding Process**:
  - HR creates employee profiles with personal and professional details
  - System automatically generates employee credentials
  - Initial password format: firstName + "1234"
  - Assignment of roles and departments

- **Employee Profile Management**:
  - Personal information (name, contact details, etc.)
  - Employment details (hire date, position, salary)
  - Professional information (skills, qualifications)
  - Document management (resume, certificates)

#### Attendance System
- **Time Tracking**:
  - Daily check-in/check-out recording
  - Automatic time calculation
  - Late arrival and early departure tracking
  - Attendance history and patterns

#### Leave Management
- **Request Processing**:
  - Multiple leave types support
  - Request submission and tracking
  - Approval workflow (Employee → HR → Approval/Rejection)
  - Leave balance tracking

- **Leave History**:
  - Complete leave record maintenance
  - Status tracking (Pending, Approved, Rejected)
  - Leave pattern analysis

#### Document Management
- **Resume Handling**:
  - Secure document upload
  - Version control for updates
  - Access control based on roles
  - Document verification and approval

## Technical Implementation

### Authentication & Security
- JWT-based authentication
- Role-based access control (RBAC)
- Secure password handling with encryption
- Session management

### Database Structure
- **User Management**:
  - AuthUser: Authentication and role information
  - User: Detailed employee information
  - Strict relationship maintenance between records

### API Organization
- RESTful endpoints grouped by functionality
- Swagger documentation for API exploration
- Secure endpoint access based on roles

## Getting Started

### Prerequisites
- Java 17+
- Maven
- Database server (configured in application.properties)

### Installation Steps

1. Clone the repository:
```bash
git clone [repository-url]
cd ERPSystem
```

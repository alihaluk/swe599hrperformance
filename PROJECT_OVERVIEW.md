# Project Overview: HR Performance App

## Introduction
The **HR Performance App** (`tr.edu.boun.hrperformance`) is an Android application designed to manage HR tasks and performance within an organization. It facilitates communication and task tracking between Employees and HR Leaders.

## Architecture
The application follows a standard Android MVC/MVP architecture:
- **Activities & Fragments (`controls`, `fragments`)**: Handle the UI and user interaction.
- **Models (`models`)**: Represent data entities like `Employee`, `HRLeader`, `EmployeeTask`.
- **Services (`services`)**: Handle data retrieval and business logic. The app uses a repository pattern with a `DataProvider` interface.

## Key Components

### 1. User Roles & Authentication
The app supports two distinct user roles, each with a tailored experience:
- **Employee**: Can view their assigned tasks and home screen.
- **HR Leader**: Can manage employees, groups, and assign tasks.

**Authentication** is handled in `LoginActivity`. Currently, the app uses a **Mock Data Provider** (`MockDataProvider`) for authentication and data storage, meaning it works offline with hardcoded data. Previous references to Firebase have been commented out.

#### Default Credentials (for testing/demo):
- **Employee**:
    - Email: `haluk.seven@boun.edu.tr`
    - Password: `haluks`
- **HR Leader**:
    - Email: `sertac@boun.edu.tr`
    - Password: `sertac`

### 2. Navigation
`MainActivity` serves as the container for the application's main content, using a `BottomNavigationView` to switch between different sections (Home, Groups, Employees, Profile). The "Home" fragment is dynamically loaded based on the logged-in user's role.

### 3. Data Management
- **`DataProvider` Interface**: Defines methods for login, fetching tasks, updating tasks, and managing employees.
- **`MockDataProvider`**: Implementation of `DataProvider` that holds data in memory. This is useful for testing and development without a live backend.

## Source Code Structure
- `app/src/main/java/tr/edu/boun/hrperformance/`
    - `adapters/`: RecyclerView adapters for lists (Employees, Tasks).
    - `controls/`: Activities (`LoginActivity`, `MainActivity`, `AddTaskActivity`).
    - `fragments/`: UI Fragments for different screens.
    - `models/`: Java POJOs representing the data.
    - `services/`: Data access layer (`DataProvider`, `MockDataProvider`).

## Build Environment
- **SDK**: Compile SDK 34, Min SDK 24.
- **Language**: Java.
- **Dependencies**: AndroidX, Material Design.

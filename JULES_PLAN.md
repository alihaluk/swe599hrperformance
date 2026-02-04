# Jules Plan: Alpha to Beta

## Mission
Move the project from Alpha to Beta Version by implementing the "Core Gameplay Loop" and ensuring the app runs autonomously without external dependencies (Firebase).

## Core Gameplay Loop
1.  **Start (Login)**: Authentication using a local mock data provider.
2.  **Play (Task Management)**: Employees can view their assigned tasks and mark them as complete.
3.  **Win/Loss (Score)**: Employees earn points for completing tasks.

## Executed Plan

### 1. Data Abstraction & Mocking
- Created `DataProvider` interface to abstract data access.
- Created `MockDataProvider` to implement the interface using in-memory data (Employees, HR Leaders, Tasks).
- Removed all Firebase dependencies from the build and source code.

### 2. Migration to AndroidX
- Migrated the entire codebase from Android Support Libraries (v26) to AndroidX (API 34) to support modern Android development and fix build issues.
- Updated `build.gradle` and `gradle-wrapper.properties` to use Gradle 8.5 and AGP 8.2.0.

### 3. Implementation of Core Loop
- **Login**: Refactored `LoginActivity` to use `MockDataProvider`.
- **Employee Home**: Refactored `EmployeeHomeFragment` to fetch tasks from `MockDataProvider`.
- **Task Completion**: Implemented logic to mark tasks as started/finished and update the score.
- **Score**: Added a score field to `Employee` model and displayed it on the Employee Home screen.

### 4. Cleanup & Consistency
- Refactored `EmployeeListFragment` and `ProfileFragment` to use `MockDataProvider`.
- Stubbed out `AddTaskActivity` and `HRLeaderHomeFragment` features that are not part of the core employee loop for Beta.
- Fixed manifest issues for Android 12+ (exported activities).

## Verification
- Project builds successfully with `./gradlew assembleDebug`.
- Tests pass with `./gradlew testDebugUnitTest`.

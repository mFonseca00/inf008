# AcadEvents

Academic Events and Participants Management System

---

## Table of Contents

- [About the Project](#about-the-project)
- [How to Run](#how-to-run)
- [Screen Flow and Features](#screen-flow-and-features)
- [Reports System](#reports-system)
- [Data Persistence](#data-persistence)
- [Validations](#validations)
- [Project Structure](#project-structure)
- [Technologies Used](#technologies-used)
- [Author](#author)

---

## About the Project

**AcadEvents** is a Java-based system for registration, listing, removal, and management of participants (students, professors, and external attendees) and academic events (lectures, courses, workshops, and fairs).  
The system is entirely command-line (console) based and uses JSON files for data persistence.

---

## How to Run

### Prerequisites

- Java 17 or higher  
- Maven

### Steps

1. **Clone the repository and navigate to the project folder:**  
   ```bash
   git clone https://github.com/mFonseca00/inf008.git
   cd Trabalho_1/acadevents
   ```

2. **Compile the project:**  
   ```bash
   mvn clean package
   ```

3. **Run the system using Maven:**  
   ```bash
   mvn exec:java
   ```

   **Note:** If the `mvn exec:java` command doesn't work, make sure your `pom.xml` contains the plugin configuration:

   ```xml
   <plugin>
     <groupId>org.codehaus.mojo</groupId>
     <artifactId>exec-maven-plugin</artifactId>
     <version>3.1.0</version>
     <configuration>
       <mainClass>acad_events.acadevents.AcadEvents</mainClass>
     </configuration>
   </plugin>
   ```

---

## Screen Flow and Features

### Main Menu

When starting, you'll see the main menu with the following options:

1. **Manage Events**: Manage events (create, delete, list)
2. **Manage Participants**: Manage participants (register, remove, list, enroll in event, generate certificate)
3. **Generate Reports**: Generate reports by type or date
4. **Generate Test Data**: Generate random test data
5. **Exit**: Exit the system

Select the desired option by typing the corresponding number.

---

### Participants Menu

When choosing "Manage Participants", you'll see:

1. **Register new participant**: Register a new participant
2. **Delete participant**: Remove participant by CPF
3. **List participants**: List all registered participants
4. **Enroll participant in event**: Enroll participant in an event
5. **Generate a event certificate**: Generate participation certificate
6. **Return to Main Menu**: Return to main menu

#### 1. Register new participant

- Enter CPF (format: `000.000.000-00`), name, email, phone (e.g., `71 91234-5678`)
- Choose type: Student, Professor, or External
- For Student: enter student ID
- For Professor: enter employee ID and department
- For External: enter organization (optional) and role
- The participant will be registered if data is valid and CPF is not already in use

#### 2. Delete participant

- Enter the CPF of the participant to be removed
- The system will confirm removal or inform if participant was not found

#### 3. List participants

- Displays all registered participants, showing CPF, name, and email

#### 4. Enroll participant in event

- Enter participant's CPF
- Choose the desired event (a list will be displayed)
- The system checks if there are available spots and if the participant type is allowed for the event
- If everything is correct, enrollment is completed

#### 5. Generate a event certificate

- Enter participant's CPF
- Choose the event they are enrolled in
- The system generates a text certificate with event and participant data
- Option to export the certificate as TXT file

#### 6. Return to Main Menu

- Returns to the system's main menu

---

### Events Menu

When choosing "Manage Events", you'll see:

1. **Create Event**: Create a new academic event
2. **Delete Event**: Remove an existing event
3. **List Events**: List all registered events
4. **Return to Main Menu**: Return to main menu

#### 1. Create Event

- The system will request common event data:
  - Title
  - Date (format: dd/MM/yyyy)
  - Location
  - Capacity (maximum number of participants)
  - Description
  - Modality (In-person, Online, or Hybrid)
- Then, you'll choose the event type:
  - **Course**: Enter coordinator, knowledge area, and total workload
  - **Lecture**: Enter speaker
  - **Workshop**: Enter instructor and duration in hours
  - **Fair**: Enter organizer and number of booths
- The event will be created and saved if there's no other event with the same title and date

#### 2. Delete Event

- You can choose how to remove the event:
  - **Remove by an attribute list**: Choose an attribute, enter the value, and select from the list of found events
  - **Remove from all events list**: Select from the complete list of events
  - **Remove from an ID**: Enter the ID of the event to be removed
- The system will confirm removal or inform if the event was not found

#### 3. List Events

- Displays all registered events, showing type, title, modality, date, and location

#### 4. Return to Main Menu

- Returns to the system's main menu

---

### Reports Menu

When choosing "Generate Reports", you'll see:

1. **Report by event type**: Generate report filtering by type (Course, Lecture, Workshop, Fair)
2. **Report by date**: Generate report filtering by date
3. **Return to Main Menu**: Return to main menu

#### 1. Report by event type

- Choose the desired event type
- The system will display all registered events of that type

#### 2. Report by date

- Enter the desired date (format: dd/MM/yyyy)
- The system will display all registered events for that date

#### 3. Return to Main Menu

- Returns to the system's main menu

---

### Generate Test Data

- The system will ask for the quantity of events and participants you want to generate
- For each data type:
  - **Events**: Random events with fictional titles, dates, modalities, and other attributes will be generated
  - **Participants**: Random participants with fictional names, CPFs, emails, and other attributes will be generated
- After generation, the system will display a success message indicating the quantity of generated data

---

### Exit

- Terminates the system and saves data to JSON files (`participants.json` and `events.json`)

---

## Reports System

The system allows generating academic event reports with two filter options: **by event type** or **by date**.

### Unified Implementation

- Uses the single method `generateReport(Scanner scan, EventReportOption reportOption)` from the `EventFunctionalities` class
- Receives the desired report type as parameter and executes the complete flow
- Searches events using `EventController` methods (`listByType` or `listByAttribute`)
- Centralizes all interaction and export logic in a single method

### Step-by-Step Operation

1. **Access Reports Menu**: Select "Generate Reports" in the main menu
2. **Filter Selection**: Choose between report by type or by date
3. **Generation and Display**: System searches and displays events according to selected filter
4. **Report Export**: Option to save report as JSON file

### Export and Organization

- **Format**: JSON files
- **Automatic folder structure**: 
  - `Events_reports_by_type/` for reports by type
  - `Events_reports_by_date/` for reports by date
- **Naming**: `[Filter]_[yyyyMMdd_HHmmss].json`
- **Sanitization**: Special characters and spaces are automatically removed
- **Versioning**: Unique timestamp to avoid overwrites

**Examples of generated files:**
- `Course_20250527_234757.json` (report by Course type)
- `01_12_2025_20250527_235039.json` (report by date 01/12/2025)

### Additional Features

- Automatic creation of destination folders if they don't exist
- Version history for auditing and tracking
- Automatic backup system to prevent data loss

---

## Data Persistence

- **Files**: `participants.json` and `events.json` in the project root
- **Loading**: Automatic on initialization (creates empty files if they don't exist)
- **Saving**: Automatic when exiting the system
- **Integrity**: Preservation of unique IDs between sessions to avoid conflicts
- **Backup**: System maintains data integrity even after restarts

---

## Validations

- **CPF**: Complete validation of format (`000.000.000-00`) and check digit
- **Email**: Standard format validation
- **Date**: dd/MM/yyyy format required
- **Phone**: Multiple accepted formats (`71 91234-5678`, `081 91234-5678`)

---

## Project Structure

```
acadevents/
├── participants.json
├── events.json
├── certificates/                          # Generated certificates
├── reports/                               # Exported reports
│   ├── Events_reports_by_type/
│   └── Events_reports_by_date/
└── src/main/java/acad_events/acadevents/
    ├── AcadEvents.java                    # Entry point
    ├── models/                            # Entities
    │   ├── event/                         # Event models
    │   └── participant/                   # Participant models
    ├── controllers/                       # Business logic
    ├── repositories/                      # JSON persistence
    ├── ui/                                # User interface
    │   ├── menu/                          # Menu system
    │   │   ├── subMenus/                  # Specific menus
    │   │   └── enums/                     # Navigation enums
    │   └── functionalities/               # Main functionalities
    │       ├── forms/                     # Input forms
    │       ├── enums/                     # Functionality enums
    │       ├── BaseFunctionalities.java
    │       ├── EventFunctionalities.java
    │       ├── ParticipantFunctionalities.java
    │       └── IntegrationFunctionalities.java
    └── common/                            # Utilities and DTOs
        ├── DTOs/                          # Data transfer objects
        └── utils/                         # General utilities
            ├── enums/                     # Shared enums
            ├── interfaces/                # System interfaces
            ├── ValidatorInputs.java       # Validations
            ├── StyleStrings.java          # Text formatting
            ├── TextBoxUtils.java          # Formatted messages
            ├── MenuUtils.java             # Menu utilities
            └── TestDataGenerator.java     # Test data generator
```

### Layer Responsibilities:

- **models/**: Domain entities (events and participants)
- **controllers/**: Business logic and coordination between layers
- **repositories/**: Persistence and JSON data manipulation
- **ui/**: User interface, menu system, and interactive functionalities
- **common/**: DTOs, shared utilities, and auxiliary components

---

## Technologies Used

- **Java 17**
- **Maven** (dependency management and build)
- **Gson** (JSON manipulation for persistence)

---

## Author

Project developed for course INF008 - IFBA  
**Marcus Vinicius Silva da Fonseca**

---
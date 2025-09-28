# Developer Guide
## Use Cases

---

## Use Case 1: Add a New Student

**Preconditions:**
- User has launched the EduDex application
- User is at the command prompt
- User has the student's information ready

**Main Success Scenario:**
1. User enters the `add` command with all required parameters
2. EduDex validates the parameters
3. EduDex adds the student to the contact list
4. EduDex shows a success message with the added student's details

**Extensions:**
- **2a. Invalid phone number format**
    - 2a1. EduDex shows error: *"Phone number must contain only numbers!"*
    - 2a2. Use case resumes at step 1

- **2b. Invalid time format**
    - 2b1. EduDex shows error: *"Enter the time as HHMM (24-hour format)"*
    - 2b2. Use case resumes at step 1

- **2c. Invalid day of week**
    - 2c1. EduDex shows error: *"Please enter a valid day of the week"*
    - 2c2. Use case resumes at step 1

- **2d. Missing required parameters**
    - 2d1. EduDex shows error with correct command format
    - 2d2. Use case resumes at step 1

---

## Use Case 2: List All Students

**Preconditions:**
- User has launched the EduDex application
- User is at the command prompt

**Main Success Scenario:**
1. User enters the `list` command
2. EduDex retrieves all student contacts
3. EduDex displays all students in a formatted list

**Extensions:**
- **3a. No students in the system**
    - 3a1. EduDex shows message: *"No students found. Use 'add' to add your first student."*
    - 3a2. Use case ends

---

## Use Case 3: Delete a Student

**Preconditions:**
- User has launched the EduDex application
- User is at the command prompt
- There is at least one student in the system

**Main Success Scenario:**
1. User enters `list` to view current students and their indices
2. User enters `delete INDEX` (where `INDEX` is a valid number)
3. EduDex removes the student at the specified index
4. EduDex shows a confirmation message

**Extensions:**
- **2a. Invalid index format (non-number)**
    - 2a1. EduDex shows error: *"INDEX must be a positive integer"*
    - 2a2. Use case resumes at step 1

- **2b. Index out of bounds**
    - 2b1. EduDex shows error: *"The student index provided is invalid"*
    - 2b2. Use case resumes at step 1

- **2c. User enters delete without viewing list first**
    - 2c1. EduDex shows error: *"Please use 'list' to view students first"*
    - 2c2. Use case resumes at step 1

---

## Use Case 4: Exit the Application

**Preconditions:**
- User has launched the EduDex application
- User is at the command prompt

**Main Success Scenario:**
1. User enters the `exit` command
2. EduDex displays a goodbye message
3. EduDex closes the application

**Extensions:**
- **2a. User has unsaved changes (for future versions)**
    - 2a1. EduDex prompts to confirm exit
    - 2a2. User confirms or cancels

---

## Use Case 5: Handle Invalid Command

**Preconditions:**
- User has launched the EduDex application
- User is at the command prompt

**Main Success Scenario:**
1. User enters an unrecognized command
2. EduDex shows error: *"Invalid command. Try again"*
3. EduDex shows available command formats as hints

**Extensions:**
- **2a. Command is partially correct**
    - 2a1. EduDex provides specific error about the incorrect parameter
    - 2a2. Use case resumes at step 1

- **2b. Command has typo**
    - 2b1. EduDex suggests possible correct commands
    - 2b2. Use case resumes at step 1

---

## Appendix B: Product Scope

### Target User Profile
- Freelance tutor who prefers using CLI
- Has many students to keep track of
- Needs to manage student details, tuition fees, and grades
- Prefers keyboard input over mouse interaction
- Comfortable with command-line interfaces

### Value Proposition
Manage student records **faster than typical GUI applications**, with optimized workflows for tutor-specific needs like scheduling, fee tracking, and grade management.

---

## Appendix C: User Stories

| Priority | As a …        | I want to …                                | So that I can…                                      |
|----------|---------------|---------------------------------------------|-----------------------------------------------------|
| ***      | new tutor      | see a guided tour with sample data          | understand how EduDex works without entering my own data first |
| ***      | tutor          | add a new student with contact details      | keep track of my students' information              |
| ***      | tutor          | view all my students at once                | get an overview of my teaching load                 |
| ***      | tutor          | delete a student who has stopped tuition    | keep my records up to date                          |
| ***      | tutor          | set default hourly rates for different subjects | maintain consistent pricing                     |
| **       | tutor          | add parents' contact information            | contact parents when needed                         |
| **       | tutor          | modify student details easily               | update information when circumstances change        |
| **       | tutor          | find students by name or subject            | quickly locate specific student records             |
| **       | tutor          | track assignment submissions                | identify students who haven't submitted work        |
| *        | tutor          | calculate monthly earnings                  | understand my income patterns                       |
| *        | expert user    | use keyboard shortcuts                      | work more efficiently                               |

**User stories not included in v1.0:**
- Opening a tuition center and sharing the app with other tutors
- Advanced analytics and reporting features
- Integration with external calendar systems

---

## Appendix E: Non-Functional Requirements

### Performance Requirements
- Should respond within 2 seconds for any command with up to 1000 students
- Should start up within 5 seconds on a standard laptop

### Usability Requirements
- Should be usable by tutors with no technical background after a brief tutorial
- Error messages should be clear and suggest corrections
- Keyboard-first interface with intuitive command structure

### Reliability Requirements
- Should not lose data under normal usage conditions
- Should handle unexpected shutdowns gracefully

### Portability Requirements
- Should work on Windows, macOS, and Linux without modification
- Should require only Java 11 or above as a dependency

### Scalability Requirements
- Should handle up to 1000 student records without performance degradation
- Data file format should allow for future expansion of fields

---

## Appendix F: Glossary

- **CLI**: Command Line Interface — A text-based interface for interacting with software
- **Tutee**: A student who receives tuition from a tutor
- **Tuition session**: A scheduled teaching period with a student
- **Hourly rate**: The amount charged per hour of tuition
- **Student profile**: The complete record of a student including contact details, subjects, and schedule
- **Assignment tracking**: Monitoring of homework submissions and deadlines
- **Default rate**: The standard hourly rate applied unless specified otherwise

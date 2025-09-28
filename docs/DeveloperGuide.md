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

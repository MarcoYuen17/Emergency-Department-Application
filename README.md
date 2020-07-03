# My Personal Project

## Emergency Department Organization System

This application  will allow **patients** to *check in*, will *show their assigned location*, *show the start/end times 
for the shifts* of **nurses/doctors**, and *show their assigned group of rooms*, if applicable. The program will also 
show the *capabilities* of each room (any special equipment they contain or special purposes). The application can be 
used by hospital staff and augmented by other IT personnel.

I am very interested in doing this project because I am currently an emergency department volunteer at BC Children's
Hospital, and the well-organized structure and flow present amazes me. In the future, I would like to apply software to
a medical setting, and this seems like a great way to start.

## User stories

- As a user, I want to be able to create a new patient and add it to the list of patients
- As a user, I want to be able to assign a patient to a room
- As a user, I want to be able to add nurses/doctors to the staff team on shift
- As a user, I want to be able to, given a nurse, see what rooms he/she has been assigned
- As a user, I want to be able to, given a room, see what nurse and patient has been assigned as well as its capabilities
- As a user, I want to be able to find the next patient who needs to be assigned a room based on urgency & time checked in
- As a user, I want to be able to, given a patient, see all their medical information
- As a user, I want to be able to, given a patient, update their urgency
- As a user, I want to be able to discharge a patient, removing them from the list of patients
- As a user, I want to be able to see all clocked in staff
- As a user, I want to be able to clock out staff members from the staff team on shift
- As a user, I want to be able to see the rooms, name, and time checked in for all patients
- As a user, I want to be able to save the current checked in patients, clocked in staff, and room assignments
- As a user, I want to be able to, when the program starts, load the state of the emergency department from file

## Instructions for Grader (Phase 3)

- You can generate the first required event by clicking "Check in a new patient" in the first row of tools, then following the prompts
    - (You can confirm this by clicking "Show all checked in patients" in the first row after checking in the patient)
- You can generate the second required event by clicking "Assign a Patient to a Room" in the first row of tools, then following the prompts
    - (You can confirm this by clicking "Show All of a Room's Information" in the second row of tools after assigning the patient to the room)
- You can trigger my audio component when you click the button in the windows created in the tools
- You can save the state of my application by clicking the "Save" button in the third row of tools
- You can reload the state of my application by clicking the "Load" button in the third row of tools

## Phase 4: Task 2

- I implemented two robust classes (ActiveStaff & CheckedInPatients)
    - ActiveStaff robust methods:
        - clockIn, clockOut
    - CheckedInPatients robust methods:
        - checkInPatient, dischargePatient
        
## Phase 4: Task 3

- The fields for ActiveStaff and CheckedInPatients in EmergencyDepartment were unnecessary as only one instance of them would ever be instantiated at a time
    - I applied the Singleton Pattern to these classes so that these fields in EmergencyDepartment could be taken out
        - This reduced the amount of coupling
- Most of the GUI methods in EmergencyDepartment, while related to the class, created a very low cohesion environment
    - I moved these methods to their respective tool classes
        - This greatly increased the cohesion of the EmergencyDepartment class
- The EmergencyDepartment class also had multiple fields which kept track of the lists of staff, patients, and rooms
    - I moved these to their respective classes
        - This also helped to increase cohesion
        
*Note to Grader: In my UML Diagram, I used composition instead of aggregation arrows for relationships between tool
classes as they seemed more appropriate although not mentioned in the course material*

# University-Timetable-Management-Application
Overview:
The Timetable Management Application is a Java-based desktop application designed to streamline the process of managing academic timetables for educational institutions. The application provides distinct interfaces for administrators, teachers, and students to manage and view course schedules efficiently.

Features:
Main Menu:
Simple navigation interface with three user roles: Admin, Teacher, and Student
Admin Features:
Secure login system (default credentials: username: admin, password: admin1)
Classroom management:
Add, view, edit, and delete classrooms
Manage classroom details including capacity, AV equipment, computer availability, and lab status
Course management:
Add, view, edit, and delete courses
Assign instructors, classrooms, days, and time slots to courses
Teacher Features:
View personalized weekly timetable
Filter timetable view by teacher name
Color-coded schedule visualization with detailed course information
Student Features:
Course selection system:
Browse and select available courses
Save personalized timetable
Automatically check for scheduling conflicts
View weekly timetable with detailed course information
Technical Requirements:
Prerequisites
Java Development Kit (JDK) 8 or higher
Swing library (included in JDK)
File Structure
The application uses CSV files for data storage:
classrooms.csv: Stores classroom details
courses.csv: Stores course information
timetable.csv: Stores student-selected courses

Installation and Setup
Ensure you have JDK 8 or higher installed on your system
Clone or download the repository
Compile the Java code:
javac TimetableManagementApp.java
Run the application:
java TimetableManagementApp

Usage Guide:
Admin
Click on the "Admin" button from the main menu
Log in using the default credentials (username: admin, password: admin1)
From the admin dashboard, you can:
Click "Manage Classrooms" to add or edit classroom information
Click "Manage Courses" to create and edit course schedules
Teacher
Click on the "Teacher" button from the main menu
Select your name from the dropdown menu to view your personalized weekly schedule
The timetable displays all assigned courses with room numbers and time slots
Student
Click on the "Student" button from the main menu
To select courses:
Click "Manage Courses"
Check the boxes next to desired courses
Click "Check Conflicts" to verify your selection has no scheduling conflicts
Click "Save Timetable" to finalize your selection
To view your schedule:
Click "Show Timetable" to see your weekly class schedule
Data Format
Classroom Data Format
Each line in classrooms.csv follows this format: RoomID,Capacity,AVEquipment,Computers,IsLab
Course Data Format
Each line in courses.csv follows this format: CourseCode,Instructor,Classroom,Day,TimeRange
Timetable Data Format
Each line in timetable.csv follows this format: CourseCode,Instructor,Classroom,Day,TimeRange

Troubleshooting
If the application fails to start, verify your Java installation
If data doesn't load, ensure the CSV files exist in the same directory as the application
If data doesn't save, check that you have write permissions for the directory

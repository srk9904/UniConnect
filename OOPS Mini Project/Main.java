package management;

import management.*;

import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.StubNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import management.organisation.attendance.*;

public class Main {
    public static void main(String[] args) {
        ManagementSystem ms = new ManagementSystem(
            "C:\\Users\\Dell\\Downloads\\OOPS Mini Project (3)\\OOPS Mini Project\\database\\students.txt",
            "C:\\Users\\Dell\\Downloads\\OOPS Mini Project (3)\\OOPS Mini Project\\database\\research_scholars.txt",
            "C:\\Users\\Dell\\Downloads\\OOPS Mini Project (3)\\OOPS Mini Project\\database\\faculties.txt",
            "C:\\Users\\Dell\\Downloads\\OOPS Mini Project (3)\\OOPS Mini Project\\database\\courses.txt",
            "C:\\Users\\Dell\\Downloads\\OOPS Mini Project (3)\\OOPS Mini Project\\database\\courses_and_faculties.txt");

        Scanner sc = new Scanner(System.in);
        int loginChoice, userType;
        String loginName;
        int loginId;

        System.out.println("WELCOME TO MANAGEMENT SYSTEM");
        do {
            System.out.println("\n1. Student\n2. Faculty\n3. Research Scholar\n4.Exit");
            System.out.print("\nEnter your choice: ");
            userType = sc.nextInt();
            sc.nextLine();
            switch (userType) {
                case 1:
                    
                    System.out.print("Enter Student Name: ");
                    loginName = sc.nextLine();
                    System.out.print("Enter Student Id: ");
                    loginId = sc.nextInt();
                    Student loggedInStudent = ms.loginStudent(loginName, loginId);
                    if(loggedInStudent!=null){
                        printStudentMenu(ms, loggedInStudent);
                    }
                    break;
                case 2:
                    System.out.print("Enter Faculty Name: ");
                    loginName = sc.nextLine();
                    System.out.print("Enter Faculty Id: ");
                    loginId = sc.nextInt();
                    Faculty loggedInFaculty = ms.loginFaculty(loginName, loginId);
                    if(loggedInFaculty!=null){
                        printFacultyMenu(ms, loggedInFaculty);
                    }
                    break;
                case 3:
                    System.out.print("Enter Scholar Name: ");
                    loginName = sc.nextLine();
                    System.out.print("Enter Scholar Id: ");
                    loginId = sc.nextInt();
                    loggedInStudent = ms.loginResearchScholar(loginName, loginId);
                    break;
                case 4:
                    break;
                default:
                    System.out.println("Invalid Input");
                    break;
            }
        } while (userType!=4);
    }

    public static void printStudentMenu(ManagementSystem ms, Student loggedInStudent){
        Scanner sc = new Scanner(System.in);
        int userChoice;
        System.out.println("1. See Courses\n2. Check Profile\n3. Check Attendacne\n4. View To Do List\n5. Edit To Do List\n6. Search Users");
        do{
            System.out.print("Enter choice: ");
            userChoice = sc.nextInt();
            sc.nextLine();
            switch(userChoice){
                case 1:
                    printStudentCourses(loggedInStudent);
                    break;
                case 2:
                    loggedInStudent.getReport();
                    break;
                case 3:
                    attendanceViewerMenu(ms, loggedInStudent);
                    break;
                case 4:
                    viewToDoList();
                    break;
                case 5:
                    System.out.println("Task to be done: ");
                    String str=sc.nextLine();
                    appendToDoList(str);
                    break;
                case 6:
                    searchUsersMenu(ms);
                    break;
                case 7:
                    break;
                default:
                    System.out.println("Invalid Input");
                    break;
            }
        } while(userChoice!=1 && userChoice!=2 && userChoice!=3 && userChoice!=4 && userChoice!=5 && userChoice!=6 && userChoice!=7);
        
    }

    public static void printStudentCourses(Student loggedInStudent) {
    System.out.println("COURSES ENROLLED BY " + loggedInStudent.getName() + ":");

    BufferedReader reader = null;

    boolean studentFound = false;

    // Open the file
    try {
        reader = new BufferedReader(new FileReader("C:\\Users\\Dell\\Downloads\\OOPS Mini Project (3)\\OOPS Mini Project\\database\\students.txt"));
        String line;

        // Read each line in the file
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(", ");
            int studentId = Integer.parseInt(parts[0]);

            // Check if the current line corresponds to the logged-in student
            if (studentId == loggedInStudent.getId()) {
                studentFound = true;
                String[] courses = parts[5].split("-");
                
                // Print the courses
                for (String course : courses) {
                    System.out.println("- " + course.trim());
                }

                // No need to continue reading the file once the student is found
                break;
            }
        }

        // If the student is not found, print the message
        if (!studentFound) {
            System.out.println("No courses enrolled.");
        }
    } catch (IOException e) {
        e.printStackTrace(); // Handle the IOException appropriately
    } finally {
        // Close the reader in the finally block
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace(); // Handle the IOException appropriately
            }
        }
    }
}




     private static void viewToDoList() {
        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\Dell\\Downloads\\OOPS Mini Project (3)\\OOPS Mini Project\\todolist.txt"))) {
            String line;
            System.out.println("To-Do List:");
            while ((line = reader.readLine()) != null) {
                System.out.println("- " + line);
            }
        } catch (IOException e) {
            System.out.println("Error reading To-Do List from file.");
        }
    }
    private static void appendToDoList(String newTask) {
        try (FileWriter fileWriter = new FileWriter("C:\\Users\\Dell\\Downloads\\OOPS Mini Project (3)\\OOPS Mini Project\\todolist.txt", true)) {
            fileWriter.write(newTask + System.lineSeparator());
            System.out.println("Task appended to the to-do list.");
        } catch (IOException e) {
            System.out.println("An error occurred while appending to the to-do list file.");
            e.printStackTrace();
        }
    }
    // private static void removeTask() {
    //     try {
    //         List<String> tasks = readToDoList();
    //         if (tasks.isEmpty()) {
    //             System.out.println("To-Do List is empty.");
    //             return;
    //         }

    //         Scanner sc = new Scanner(System.in);
    //         System.out.print("Enter the task number to remove: ");
    //         int taskNumber = sc.nextInt();
    //         sc.nextLine();  // Consume the newline character

    //         if (taskNumber >= 1 && taskNumber <= tasks.size()) {
    //             tasks.remove(taskNumber - 1);
    //             writeToDoList(tasks);
    //             System.out.println("Task removed successfully.");
    //         } else {
    //             System.out.println("Invalid task number.");
    //         }
    //     } catch (IOException e) {
    //         System.out.println("Error removing task from the To-Do List.");
    //     }
    // }

    // private static List<String> readToDoList() throws IOException {
    //     List<String> tasks = new ArrayList<>();
    //     try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\Dell\\Downloads\\OOPS Mini Project (3)\\OOPS Mini Project\\todolist.txt"))) {
    //         String line;
    //         while ((line = reader.readLine()) != null) {
    //             tasks.add(line);
    //         }
    //     }
    //     return tasks;
    // }

//     private static void writeToDoList(List<String> tasks) throws IOException {
//         try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\Dell\\Downloads\\OOPS Mini Project (3)\\OOPS Mini Project\\todolist.txt"))) {
//             for (String task : tasks) {
//                 writer.write(task);
//                 writer.newLine();
//             }
//         }
//     }
// }

    public static void attendanceViewerMenu(ManagementSystem ms, Student s){
        System.out.println("ATTENDANCE VIEWER");
        AttendanceViewer av = s.getAttendanceViewer();
        Student sTemp = new Student(s.getName(), s.getId(), s.getDob(), s.getAddress(), s.getDept(), s.getAttendanceViewer());
        int choice;
        String tempString;
        Scanner sc = new Scanner(System.in);
        do{
            av.printAttendanceReport();
            System.out.println("1. Mark Present for a Course\n2. Marks Absent for a Course\n3. Reset Attendance\n4. Exit Attendance Viewer");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            switch(choice){
                case 1:
                    System.out.println("Enter the Course to mark present: ");
                    sc.nextLine();
                    tempString = sc.nextLine();
                    av.markPresent(tempString.trim());
                    break;
                case 2:
                    System.out.println("Enter the course to mark Absent: ");
                    sc.nextLine();
                    tempString = sc.nextLine();
                    av.markAbsent(tempString.trim());
                    break;
                case 3:
                    System.out.println("Enter the course to Reset Attendance: ");
                    sc.nextLine();
                    tempString = sc.nextLine();
                    av.resetAttendance(tempString.trim());
                    break;
                case 4:
                    break;
                default:
                    System.out.println("Invalid Input");
                    break;
            }
        }while(choice!=4);
        ms.updateStudents(sTemp, s);
    }

    public static void printFacultyMenu(ManagementSystem ms, Faculty loggedInFaculty) {
        Scanner sc = new Scanner(System.in);
        int userChoice;
        System.out.println("1. See Courses\n2. Check Profile\n3. Promote Scholar to Faculty\n4. Add Course\n5. Remove Course\n6. Search Users\n7. Exit");
        do {
            System.out.print("Enter choice: ");
            userChoice = sc.nextInt();
            switch (userChoice) {
                case 1:
                    printFacultyCourses(loggedInFaculty);
                    break;
                case 2:
                    loggedInFaculty.getReport();
                    break;
                case 3:
                    loggedInFaculty.promoteScholarToFacultyMenu(ms);
                    break;
                case 4:
                    System.out.print("Enter the course to add: ");
                    String courseToAdd = sc.next();
                    loggedInFaculty.addCourse(courseToAdd);
                    System.out.println("Course added successfully.");
                    break;
                case 5:
                    System.out.print("Enter the course to remove: ");
                    String courseToRemove = sc.next();
                    loggedInFaculty.removeCourse(courseToRemove);
                    System.out.println("Course removed successfully.");
                    break;
                case 6:
                    searchUsersMenu(ms);
                    break;
                case 7:
                    break;
                default:
                    System.out.println("Invalid Input");
                    break;
            }
        } while (userChoice != 7);
    }


    public static void printFacultyCourses(Faculty loggedInFaculty) {
        System.out.println("COURSES TAUGHT BY " + loggedInFaculty.getName() + ":");
    
        BufferedReader reader = null;
    
        boolean facultyFound = false;
    
        // Open the file
        try {
            reader = new BufferedReader(new FileReader("C:\\Users\\Dell\\Downloads\\OOPS Mini Project (3)\\OOPS Mini Project\\database\\faculties.txt"));
            String line;
    
            // Read each line in the file
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                int facultyId = Integer.parseInt(parts[0]);
    
                // Check if the current line corresponds to the logged-in faculty
                if (facultyId == loggedInFaculty.getId()) {
                    facultyFound = true;
                    String[] courses = parts[5].split("-");
                    
                    // Print the courses
                    for (String course : courses) {
                        System.out.println("- " + course.trim());
                    }
    
                    // No need to continue reading the file once the faculty is found
                    break;
                }
            }
    
            // If the faculty is not found, print the message
            if (!facultyFound) {
                System.out.println("No courses assigned.");
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle the IOException appropriately
        } finally {
            // Close the reader in the finally block
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace(); // Handle the IOException appropriately
                }
            }
        }
    }
    
    public static void searchUsersMenu(ManagementSystem ms) {
        Scanner sc = new Scanner(System.in);
        int userSearch, searchQuery;
        System.out.println("1. Search Students\n2. Search Faculties\n3. Search Research Scholars");
        do {
            System.out.print("Enter choice: ");
            userSearch = sc.nextInt();
            switch (userSearch) {
                case 1:
                    System.out.println("Search Student By");
                    System.out.println("1. Name\n2. Id\n3. Date of Birth\n4.Department\n5.Course");
                    do {
                        TreeSet<Student> foundUsers = new TreeSet<Student>();
                        System.out.print("Enter choice: ");
                        searchQuery = sc.nextInt();
                        switch (searchQuery) {
                            case 1:
                                System.out.print("Enter name: ");
                                sc.nextLine();
                                String name = sc.nextLine();
                                foundUsers = ms.searchStudent(name.trim());
                                break;
                            case 2:
                                System.out.print("Enter Student Id: ");
                                int id = sc.nextInt();
                                foundUsers = ms.searchStudent(id);
                                break;
                            case 3:
                                try {
                                    System.out.print("Enter date of birth(dd-MM-yyyy): ");
                                    sc.nextLine();
                                    String dob = sc.nextLine();
                                    Date date = new SimpleDateFormat("dd-MM-yyyy").parse(dob);
                                    foundUsers = ms.searchStudent(date);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case 4:
                                System.out.print("Enter department: ");
                                sc.nextLine();
                                String dept = sc.nextLine();
                                foundUsers = ms.searchStudentByDept(dept.trim());
                                break;
                            case 5:
                                System.out.print("Enter course: ");
                                sc.nextLine();
                                String course = sc.nextLine();
                                foundUsers = ms.searchStudentByCourse(course.trim());
                                break;
                            default:
                                System.out.println("Invalid Input");
                                System.out.println("Exiting Search Program! \n");
                                break;
                        }
                        ms.printUsersList(foundUsers);
                    } while (searchQuery != 1 && searchQuery != 2 && searchQuery != 3 && searchQuery != 4
                            && searchQuery != 5);
                    break;
                case 2:
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Invalid Input");
                    break;
            }
        } while (userSearch != 1 && userSearch != 2 && userSearch != 3);
    }
}

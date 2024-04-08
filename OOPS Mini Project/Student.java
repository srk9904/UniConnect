package management;

import java.util.Date;
import java.util.HashSet;
import management.*;
import management.organisation.attendance.AttendanceViewer;

public class Student extends Person {
    private static int lastAssignedId = 100; // Starting from 100
    private String dept;
    private int id;
    private HashSet<String> courses;
    private AttendanceViewer attendanceViewer;
    // private ToDoList toDoList;
    // private MarkManager marksManager;

    public Student(String name, Date dob, String address, String dept, AttendanceViewer attendanceViewer) {
        super(name, dob, address);
        this.dept = dept;
        this.id = generateId();
        this.courses = new HashSet<>();
        this.attendanceViewer = attendanceViewer;
        // this.toDoList = new ToDoList();
        // this.marksManager = new MarkManager();
    }
    public Student(String name, Date dob, String address, String dept) {
        super(name, dob, address);
        this.dept = dept;
        this.id = generateId();
        this.courses = new HashSet<>();
        // this.attendanceViewer = attendanceViewer;
        // this.toDoList = new ToDoList();
        // this.marksManager = new MarkManager();
    }
    public Student(String name, int id, Date dob, String address, String dept, AttendanceViewer attendanceViewer) {
        super(name, dob, address);
        this.dept = dept;
        this.id = id;
        this.courses = new HashSet<>();
        this.attendanceViewer = attendanceViewer;
        // this.toDoList = new ToDoList();
        // this.marksManager = new MarkManager();
    }

    private synchronized int generateId() {
        lastAssignedId++;
        return lastAssignedId;
    }
  //Assigns id to the students from 100

    public String getDept() {
        return dept;
    }

    public int getId(){
        return id;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public HashSet<String> getCourses() {
        return courses;
    }

    public AttendanceViewer getAttendanceViewer() {
        return attendanceViewer;
    }

    public void getReport(){
        System.out.println("STUDENT DETAILS");
        System.out.println("Name: " + getName());
        System.out.println("Address: " + getAddress());
        System.out.println("Department: " + getDept());
    }

}

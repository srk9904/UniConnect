package management.course;

import java.util.*;

import management.*;
import management.course.assignment.Assignment;

public class Course<T extends Person> {
    private String courseId;
    private String courseName;
    private Faculty faculty;
    private TreeSet<T> students;
    private LinkedHashSet<Assignment> assignments;
    private final static int LIMIT = 100;

    public Course(Faculty faculty, String courseId, String course, LinkedHashSet<Assignment> assignments) {
        this.faculty = faculty;
        this.courseName = course;
        this.courseId = courseId;
        this.assignments = assignments;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public String getCourseName() {
        return courseName;
    }

    public static int getLimit() {
        return LIMIT;
    }
}

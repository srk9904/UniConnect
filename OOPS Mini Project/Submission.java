package management.course.assignment;

import management.course.assignment.Assignment;

import java.sql.Date;

import management.Student;

public class Submission extends Assignment {
    private Student student;
    private Date submissionDate;
    private boolean isLate;

    public Submission(Student student, Date submissionDate, boolean isLate, String file, Date deadline) {
        super(file, deadline);
        this.student = student;
        this.submissionDate = new Date(submissionDate.getTime());
        this.isLate = isLate;
    }

    public Student getStudent() {
        return student;
    }

    public Date getSubmissionDate() {
        return submissionDate;
    }

    public boolean getLateStatus() {
        return isLate;
    }
}

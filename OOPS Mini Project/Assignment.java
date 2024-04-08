package management.course.assignment;

import java.sql.Date;
import java.util.LinkedHashSet;

import management.course.assignment.Submission;

public class Assignment {
    private String file;
    private Date deadline;
    private LinkedHashSet<Submission> submissions;

    public Assignment(String file) {
        this.file = file;
        deadline = null;
        submissions = new LinkedHashSet<Submission>();
    }

    public Assignment(String file, Date deadline) {
        this.file = file;
        this.deadline = new Date(deadline.getTime());
        submissions = new LinkedHashSet<Submission>();
    }


    public Assignment(String file, Date deadline, LinkedHashSet<Submission> submissions) {
        this.file = file;
        this.deadline = new Date(deadline.getTime());
        submissions = new LinkedHashSet<Submission>();
    }

    public LinkedHashSet<Submission> getSubmissions() {
        return submissions;
    }

    public Date getDeadline() {
        return deadline;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public void addSubmission(Submission submission){
        submissions.add(submission);
    }
}

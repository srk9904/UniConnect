package management.organisation.attendance;


public class CourseAttendance {
    private int noOfPresent;
    private int totalClasses;

    public CourseAttendance(){
        noOfPresent = 0;
        totalClasses = 0;
    }
    public CourseAttendance(int noOfPresent, int totalClasses){
        this.noOfPresent = noOfPresent;
        this.totalClasses = totalClasses;
    }
    public int getNoOfPresent() {
        return noOfPresent;
    }
    public int getTotalClasses() {
        return totalClasses;
    }
    public void setNoOfPresent(int noOfPresent) {
        this.noOfPresent = noOfPresent;
    }
    public void setTotalClasses(int totalClasses) {
        this.totalClasses = totalClasses;
    }
    public double calcAttendance(){
        return (double)noOfPresent/totalClasses*100;
    }
}

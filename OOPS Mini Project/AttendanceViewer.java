package management.organisation.attendance;

import java.util.*;

import management.organisation.attendance.CourseAttendance;



public class AttendanceViewer{
    private LinkedHashMap<String, CourseAttendance> courseAttendance;
    public AttendanceViewer(){
        courseAttendance = new LinkedHashMap<String, CourseAttendance>();
    }
    public LinkedHashMap<String, CourseAttendance> getCourseAttendance() {
        return courseAttendance;
    }
    public void markPresent(String course){
        for(Map.Entry<String, CourseAttendance> entry : courseAttendance.entrySet()){
            if(entry.getKey().trim().equals(course.trim())){
                entry.getValue().setNoOfPresent(entry.getValue().getNoOfPresent()+1);
                entry.getValue().setTotalClasses(entry.getValue().getTotalClasses()+1);
            }
        }
    }
    public void markAbsent(String course){
        for(Map.Entry<String, CourseAttendance> entry : courseAttendance.entrySet()){
            if(entry.getKey().trim().equals(course.trim())){
                entry.getValue().setTotalClasses(entry.getValue().getTotalClasses()+1);
            }
        }
    }
    public void resetAttendance(String course){
        for(Map.Entry<String, CourseAttendance> entry : courseAttendance.entrySet()){
            if(entry.getKey().trim().equals(course.trim())){
                entry.getValue().setNoOfPresent(0);
                entry.getValue().setTotalClasses(0);
            }
        }
    }
    public void addCourseAttendance(String course){
        courseAttendance.put(course, new CourseAttendance());
    }
    public void addCourseAttendance(String course, int noOfPresent, int totalClasses){
        courseAttendance.put(course, new CourseAttendance(noOfPresent, totalClasses));
    }
    public void printAttendanceReport(){
        System.out.println("Course Name                   | No of Present | Total Classes | Attendance Percent");
        for(Map.Entry<String, CourseAttendance> entry : courseAttendance.entrySet()){
            System.out.println(entry.getKey() + "         " + entry.getValue().getNoOfPresent() + "        " + entry.getValue().getTotalClasses() + "       " + entry.getValue().calcAttendance());
        }
    }

}

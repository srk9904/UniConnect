package management;

import management.interfaces.Manageable;
// import management.Faculty;

import java.util.Date;
import java.util.HashSet;

public final class ResearchScholar extends Student implements Manageable {

  public ResearchScholar(String name, Date dob, String address, String dept) {
    super(name, dob, address, dept);
  }

  // public void changeDesignation(Faculty faculty, String desig) {
  //   // Assuming there is a method in the Faculty class to change the designation
  //   // faculty.changeDesignation(this, desig);
  // }

  @Override
  public void addCourse(String course) {
    // Assuming there is a method in the Student class to add a course
    // getCourses().add(course);
  }

  @Override
  public void removeCourse(String course) {
    // Assuming there is a method in the Student class to remove a course
    // getCourses().remove(course);
  }

}

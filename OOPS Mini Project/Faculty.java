package management;

import management.interfaces.Manageable;
import management.ResearchScholar;
import management.course.Course;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.HashSet;
import java.util.Scanner;

public final class Faculty extends Person implements Manageable {
  private static int lastAssignedId = 1000; // Starting from 1000
  private int id;
  private String dept;
  private HashSet<String> courses;

  public Faculty(String name, Date dob, String address, String dept) {
    super(name, dob, address);
    this.id = generateId();
    this.dept = dept;
    this.courses = new HashSet<String>();
  }

  private synchronized int generateId() {
    lastAssignedId++;
    return lastAssignedId;
  }

  public int getId() {
    return id;
  }

  public String getDept() {
    return dept;
  }

  public void setDept(String dept) {
    this.dept = dept;
  }

  public HashSet<String> getCourses() {
    return courses;
  }

  public void addCourse(String courseToAdd) {
    courses.add(courseToAdd);

    // Update the faculties.txt file
    updateFacultiesFile();
}

// Remove a course for the faculty
public void removeCourse(String courseToRemove) {
    courses.remove(courseToRemove);

    // Update the faculties.txt file
    updateFacultiesFile();
}


  public void promoteScholarToFacultyMenu(ManagementSystem ms) {
    Scanner sc = new Scanner(System.in);
    System.out.print("Enter the name of the scholar to promote: ");
    String scholarName = sc.next();


    ResearchScholar scholarToPromote = null;
    for (ResearchScholar rs : ms.getResearchScholars()) {

      if (rs.getName().trim().equals(scholarName.trim())) {
        scholarToPromote = rs;
        break;
      }
    }

    if (scholarToPromote != null) {
  
      removeScholarFromFile("C:\\Users\\Dell\\Downloads\\OOPS Mini Project (3)\\OOPS Mini Project\\database\\research_scholars.txt", scholarToPromote);

      Faculty promotedFaculty = new Faculty(scholarToPromote.getName(), scholarToPromote.getDob(),
          scholarToPromote.getAddress(), scholarToPromote.getDept());


      ms.addFaculty(promotedFaculty);

      ms.removeResearchScholars(scholarToPromote);

      System.out.println("Scholar promoted to Faculty successfully.");
    } else {
      System.out.println("Scholar not found.");
    }
  }

  // Helper method to remove scholar from the research_scholar.txt file
  private static void removeScholarFromFile(String scholarsFile, ResearchScholar scholarToRemove) {
    try {
        File inputFile = new File(scholarsFile);
        File tempFile = new File("C:\\Users\\Dell\\Downloads\\OOPS Mini Project (3)\\OOPS Mini Project\\database\\temp_research_scholars.txt");

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String currentLine;

        while ((currentLine = reader.readLine()) != null) {
            if (!currentLine.trim().contains(scholarToRemove.getName().trim())) {
                writer.write(currentLine + System.lineSeparator());
            }
        }

        writer.close();
        reader.close();

        // Now append the promoted scholar to faculties.txt
        BufferedWriter facultiesWriter = new BufferedWriter(new FileWriter(new File("C:\\Users\\Dell\\Downloads\\OOPS Mini Project (3)\\OOPS Mini Project\\database\\faculties.txt"), true));
        facultiesWriter.write(scholarToRemove.toString() + System.lineSeparator());
        facultiesWriter.close();

        // Rename the temp file to the original file
        tempFile.renameTo(inputFile);
    } catch (IOException e) {
        e.printStackTrace();
    }
}

// Helper method to update the faculties.txt file
private void updateFacultiesFile() {
  try {
      File fileToUpdate = new File("C:\\Users\\Dell\\Downloads\\OOPS Mini Project (3)\\OOPS Mini Project\\database\\faculties.txt");

      BufferedReader reader = new BufferedReader(new FileReader(fileToUpdate));
      StringBuilder updatedContent = new StringBuilder();

      String currentLine;

      while ((currentLine = reader.readLine()) != null) {
          String[] parts = currentLine.split(", ");
          int facultyId = Integer.parseInt(parts[0]);

          if (facultyId == this.getId()) {
              // Update the courses for the current faculty
              parts[5] = String.join("-", courses);
          }

          updatedContent.append(String.join(", ", parts)).append(System.lineSeparator());
      }

      reader.close();

      // Write the updated content back to the original file
      FileWriter writer = new FileWriter(fileToUpdate);
      writer.write(updatedContent.toString());
      writer.close();
  } catch (IOException e) {
      e.printStackTrace();
  }
}



  // public void addCourse(String course) {
  //   courses.add(course);
  // }

  // public void removeCourse(String course) {
  //   courses.remove(course);
  // }

  public void getReport() {
    System.out.println("FACULTY DETAILS");
    System.out.println("Faculty Id: " + getId());
    System.out.println("Name: " + getName());
    System.out.println("Address: " + getAddress());
    System.out.println("Department: " + getDept());
  }

}

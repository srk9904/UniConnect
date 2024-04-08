package management;

import management.*;
import management.course.assignment.*;
import management.course.*;
import management.organisation.attendance.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.StubNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.naming.directory.InvalidAttributesException;
import javax.security.auth.Subject;

class UserComparator<T extends Person> implements Comparator<T> {
    public int compare(T s1, T s2) {
        if (s1.getId() < s2.getId()) {
            return -1;
        } else {
            return 1;
        }
    }
}

public class ManagementSystem {
    private HashSet<Student> students;
    private HashSet<ResearchScholar> researchScholars;
    private HashSet<Faculty> faculties;
    private HashMap<String, String> courses;
    private HashSet<Course> subjects;
    private String studentsFile, scholarsFile, facultiesFile, coursesFile;

    public ManagementSystem(String studentFile, String scholarsFile, String facultiesFile, String coursesFile,
            String subjectsFile) {
        this.studentsFile = studentFile;
        this.scholarsFile = scholarsFile;
        this.facultiesFile = facultiesFile;
        this.coursesFile = coursesFile;
        students = new HashSet<Student>();
        try {
            String currentLine;
            BufferedReader studentBufferedReader = new BufferedReader(new FileReader(studentFile));
            while ((currentLine = studentBufferedReader.readLine()) != null) {
                String[] splitString = currentLine.split(",");
                String attendance = splitString[6];
                // System.out.println(attendance);
                AttendanceViewer av = new AttendanceViewer();
                String[] courseAttendance = attendance.split("/");
                for (String ca : courseAttendance) {
                    String[] s = ca.split("-");
                    av.addCourseAttendance(s[0], Integer.parseInt(s[1]), Integer.parseInt(s[2]));
                }
                Student studentTemp = new Student(splitString[1].trim(),
                        new SimpleDateFormat("dd-MM-yyyy").parse(splitString[2]), splitString[3].trim(),
                        splitString[4].trim(), av);

                students.add(studentTemp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // subjects = new HashSet<Course>();
        // try{
        //     String currentLine;
        //     BufferedReader studentBufferedReader = new BufferedReader(new FileReader(studentFile));
        //     while ((currentLine = studentBufferedReader.readLine()) != null) {
        //         String[] splitString = currentLine.split(",");
        //         String[] submissions = splitString[6].split("#");
        //         LinkedHashSet<Submission> submissionsClass;
        //         for(String s : submissions){
        //             String individualSubmission[] = s.split("/");
        //             boolean isLate;
        //             if(individualSubmission[2].trim().equals("fasle"))
        //                 isLate = false;
        //             else
        //                 isLate = true;
                    
        //             Student sTemp = getStudent(individualSubmission[0])

        //             submissionsClass.add(new Submission(sTemp, new SimpleDateFormat("dd-MM-yyyy").parse(individualSubmission[1]), isLate, splitString[3], new SimpleDateFormat("dd-MM-yyyy").parse(splitString[5])));
        //         }
            
        //         Assignment assignment = new Assignment(splitString[3], new SimpleDateFormat("dd-MM-yyyy").parse(splitString[5]));
        //         Course course = new Course(splitString[2], splitString[0], splitString[1],)

        //         students.add(studentTemp);
        //     }
        // }
        // try {
        //     String currentLine;
        //     BufferedReader subjectBufferedReader = new BufferedReader(new FileReader(subjectsFile));
        //     while ((currentLine = subjectBufferedReader.readLine()) != null) {
        //         String[] splitString = currentLine.split(",");
        //         String courseId = splitString[0].trim();
        //         String subjectName = splitString[1].trim();
        //         String facultyName = splitString[2].trim();
        //         String studentNames = splitString[3].trim();
        //         String filePath = splitString[4].trim();
        //         Date deadline = new SimpleDateFormat("dd-MM-yyyy").parse(splitString[5].trim());

        //         String submissionsInfo = splitString[6].trim();
        //         LinkedHashSet<Submission> submissions = parseSubmissionsInfo(submissionsInfo);

        //         Subject subjectTemp = new Subject(courseId, subjectName, facultyName, studentNames, filePath, deadline,
        //                 submissions);
        //         subjects.add(subjectTemp);
        //     }
        // } catch (IOException e) {
        //     e.printStackTrace();
        // } catch (ParseException e) {
        //     e.printStackTrace();
        // }

        researchScholars = new HashSet<ResearchScholar>();
        try {
            String currentLine;
            BufferedReader studentBufferedReader = new BufferedReader(new FileReader(scholarsFile));
            while ((currentLine = studentBufferedReader.readLine()) != null) {
                String[] splitString = currentLine.split(",");
                ResearchScholar scholarTemp = new ResearchScholar(splitString[1],
                        new SimpleDateFormat("dd-MM-yyyy").parse(splitString[2]), splitString[3], splitString[4]);

                researchScholars.add(scholarTemp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        faculties = new HashSet<Faculty>();
        try {
            String currentLine;
            BufferedReader studentBufferedReader = new BufferedReader(new FileReader(facultiesFile));
            while ((currentLine = studentBufferedReader.readLine()) != null) {
                String[] splitString = currentLine.split(",");
                Faculty facultyTemp = new Faculty(splitString[1],
                        new SimpleDateFormat("dd-MM-yyyy").parse(splitString[2]), splitString[3], splitString[4]);

                faculties.add(facultyTemp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public HashSet<Student> getStudents() {
        return students;
    }

    public HashMap<String, String> getCourses() {
        return courses;
    }

    public HashSet<Faculty> getFaculties() {
        return faculties;
    }

    public HashSet<ResearchScholar> getResearchScholars() {
        return researchScholars;
    }

    public Student getStudent(String name){
        for(Student s : students){
            if(s.getName().equals(name)){
                return s;
            }
        }
        return null;
    }

    // Student Search Functions
    public TreeSet<Student> searchStudent(String name) {
        TreeSet<Student> foundStudents = new TreeSet<Student>(new UserComparator<Student>());
        for (Student s : students) {
            if (s.getName().equals(name)) {
                foundStudents.add(s);
            }
        }
        return foundStudents;
    }

    public TreeSet<Student> searchStudent(int id) {
        TreeSet<Student> foundStudents = new TreeSet<Student>(new UserComparator<Student>());
        for (Student s : students) {
            if (s.getId() == id) {
                foundStudents.add(s);
            }
        }
        return foundStudents;
    }

    public TreeSet<Student> searchStudent(Date dob) {
        TreeSet<Student> foundStudents = new TreeSet<Student>(new UserComparator<Student>());
        for (Student s : students) {
            if (s.getDob().compareTo(dob) == 0) {
                foundStudents.add(s);
            }
        }
        return foundStudents;
    }

    public TreeSet<Student> searchStudentByCourse(String course) {
        TreeSet<Student> foundStudents = new TreeSet<Student>(new UserComparator<Student>());
        for (Student s : students) {
            HashSet<String> studentCourses = s.getCourses();
            if (studentCourses.contains(course)) {
                foundStudents.add(s);
            }
        }
        return foundStudents;
    }

    public TreeSet<Student> searchStudentByDept(String dept) {
        TreeSet<Student> foundStudents = new TreeSet<Student>(new UserComparator<Student>());
        for (Student s : students) {
            if (s.getDept().equals(dept)) {
                foundStudents.add(s);
            }
        }
        return foundStudents;
    }

    // ResearchScholar search Functions
    public TreeSet<ResearchScholar> searchResearchScholars(String name) {
        TreeSet<ResearchScholar> foundStudents = new TreeSet<ResearchScholar>(new UserComparator<ResearchScholar>());
        for (ResearchScholar rs : researchScholars) {
            if (rs.getName().equals(name)) {
                foundStudents.add(rs);
            }
        }
        return foundStudents;
    }

    public TreeSet<ResearchScholar> searchResearchScholars(int id) {
        TreeSet<ResearchScholar> foundStudents = new TreeSet<ResearchScholar>(new UserComparator<ResearchScholar>());
        for (ResearchScholar rs : researchScholars) {
            if (rs.getId() == id) {
                foundStudents.add(rs);
            }
        }
        return foundStudents;
    }

    public TreeSet<ResearchScholar> searchResearchScholars(Date dob) {
        TreeSet<ResearchScholar> foundStudents = new TreeSet<ResearchScholar>(new UserComparator<ResearchScholar>());
        for (ResearchScholar rs : researchScholars) {
            if (rs.getDob().compareTo(dob) == 0) {
                foundStudents.add(rs);
            }
        }
        return foundStudents;
    }

    public TreeSet<ResearchScholar> searchResearchScholarsByCourse(String course) {
        TreeSet<ResearchScholar> foundStudents = new TreeSet<ResearchScholar>(new UserComparator<ResearchScholar>());
        for (ResearchScholar rs : researchScholars) {
            HashSet<String> scholarCourses = rs.getCourses();
            if (scholarCourses.contains(course)) {
                foundStudents.add(rs);
            }
        }
        return foundStudents;
    }

    public TreeSet<ResearchScholar> searchResearchScholarsByDept(String dept) {
        TreeSet<ResearchScholar> foundStudents = new TreeSet<ResearchScholar>(new UserComparator<ResearchScholar>());
        for (ResearchScholar rs : researchScholars) {
            if (rs.getDept().equals(dept)) {
                foundStudents.add(rs);
            }
        }
        return foundStudents;
    }

    // Faculty Search Functions
    public TreeSet<Faculty> searchFaculties(String name) {
        TreeSet<Faculty> foundStudents = new TreeSet<Faculty>(new UserComparator<Faculty>());
        for (Faculty f : faculties) {
            if (f.getName().equals(name)) {
                foundStudents.add(f);
            }
        }
        return foundStudents;
    }

    public TreeSet<Faculty> searchFaculties(int id) {
        TreeSet<Faculty> foundStudents = new TreeSet<Faculty>(new UserComparator<Faculty>());
        for (Faculty f : faculties) {
            if (f.getId() == id) {
                foundStudents.add(f);
            }
        }
        return foundStudents;
    }

    public TreeSet<Faculty> searchFaculties(Date dob) {
        TreeSet<Faculty> foundStudents = new TreeSet<Faculty>(new UserComparator<Faculty>());
        for (Faculty f : faculties) {
            if (f.getDob().compareTo(dob) == 0) {
                foundStudents.add(f);
            }
        }
        return foundStudents;
    }

    public TreeSet<Faculty> searchFacultiesByCourse(String course) {
        TreeSet<Faculty> foundStudents = new TreeSet<Faculty>(new UserComparator<Faculty>());
        for (Faculty f : faculties) {
            HashSet<String> scholarCourses = f.getCourses();
            if (scholarCourses.contains(course)) {
                foundStudents.add(f);
            }
        }
        return foundStudents;
    }

    public TreeSet<Faculty> searchFacultiesByDept(String dept) {
        TreeSet<Faculty> foundStudents = new TreeSet<Faculty>(new UserComparator<Faculty>());
        for (Faculty f : faculties) {
            if (f.getDept().equals(dept)) {
                foundStudents.add(f);
            }
        }
        return foundStudents;
    }

    // Add Entities
    public void addCourse(String course, String courseId) {
        courses.put(courseId, course);
    }

    public void addFaculty(Faculty f) {
        faculties.add(f);
    }

    public void addStudent(Student s) {
        students.add(s);
    }

    public void addResearchScholars(ResearchScholar rs) {
        researchScholars.add(rs);
    }

    // Remove entities
    public void removeCourse(String course, String courseId) {
        courses.remove(courseId, course);
    }

    public void removeFaculty(Faculty f) {
        faculties.remove(f);
    }

    public void removeStudent(Student s) {
        students.remove(s);
    }

    public void removeResearchScholars(ResearchScholar rs) {
        researchScholars.remove(rs);
    }

    // Login Module
    public Student loginStudent(String name, int id){

        int check=0;
        for(Student s : students){
            if((s.getName()).trim().equals(name.trim()) && s.getId()==id){
                check=1;
                return s;
            }
        }
        return null;
    }
    public ResearchScholar loginResearchScholar(String name, int id){
        int check=0;
        for(ResearchScholar rs : researchScholars){
            if(rs.getName().trim().equals(name.trim()) && rs.getId()==id){
                check=1;
                return rs;
            }
        }
        return null;
    }
    public Faculty loginFaculty(String name, int id) {
        int check=0;
        for(Faculty f : faculties){
            if(f.getName().trim().equals(name.trim()) && f.getId()==id){
                check=1;
                return f;
            }
        }
        return null;
    }

    // updateFile
    public void updateStudents(Student oldS1, Student new_S1) {
        String old = oldS1.getId() + ", " + oldS1.getName() + oldS1.getDob().getDate() + "-" + oldS1.getDob().getMonth()
                + "-" + oldS1.getDob().getYear() + oldS1.getDept();
        String oldCourses = "";
        for (String s : oldS1.getCourses()) {
            oldCourses += (s + "-");
        }
        old += (", " + oldCourses.substring(0, oldCourses.length() - 2));
        String oldAttendance = "";
        for (Map.Entry<String, CourseAttendance> entry : oldS1.getAttendanceViewer().getCourseAttendance().entrySet()) {
            oldAttendance += (entry.getKey() + "-" + entry.getValue().getNoOfPresent() + "-"
                    + entry.getValue().getTotalClasses() + "/");
        }
        old += (", " + oldAttendance);

        String new_ = new_S1.getId() + ", " + new_S1.getName() + new_S1.getDob().getDate() + "-"
                + new_S1.getDob().getMonth() + "-" + new_S1.getDob().getYear() + new_S1.getDept();
        String new_Courses = "";
        for (String s : new_S1.getCourses()) {
            new_Courses += (s + "-");
        }
        new_ += (", " + new_Courses.substring(0, new_Courses.length() - 2));
        String new_Attendance = "";
        for (Map.Entry<String, CourseAttendance> entry : new_S1.getAttendanceViewer().getCourseAttendance()
                .entrySet()) {
            new_Attendance += (entry.getKey() + "-" + entry.getValue().getNoOfPresent() + "-"
                    + entry.getValue().getTotalClasses() + "/");
        }
        new_ += (", " + new_Attendance);
        // Scanner sc;
        // try{
        // sc = new Scanner(new File(studentsFile));
        // } catch(Exception e){
        // e.printStackTrace();
        // }
        // StringBuffer buffer = new StringBuffer();
        // while(sc.hasNext()){
        // buffer.append(sc.nextLine() + System.lineSeparator());
        // }
        // String fileContents = buffer.toString();
        // sc.close();

        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(studentsFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String fileContents = resultStringBuilder.toString();

        fileContents = fileContents.replaceAll(old, new_);
        try {
            FileWriter writer = new FileWriter(studentsFile);
            writer.append(fileContents);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Miscallaneous
    public String getCourseId(String courseName) {
        for (Map.Entry<String, String> entry : courses.entrySet()) {
            if (entry.getValue().equals(courseName)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public <T extends Person> void printUsersList(TreeSet<T> users) {
        for (T u : users) {
            System.out.println(u.getId() + " " + u.getName());
        }
    }
}


// // Helper method to parse submissions information
// private LinkedHashSet<Submission> parseSubmissionsInfo(String submissionsInfo) {
//     LinkedHashSet<Submission> submissions = new LinkedHashSet<>();
//     String[] submissionArray = submissionsInfo.split("#");
//     for (String submissionInfo : submissionArray) {
//         String[] submissionDetails = submissionInfo.split("/");
//         String studentName = submissionDetails[0].trim();
//         Date submissionDate = parseDate(submissionDetails[1].trim());
//         boolean isLate = Boolean.parseBoolean(submissionDetails[2].trim());

//         submissions.add(new Submission(studentName, submissionDate, isLate));
//     }
//     return submissions;
// }



// Helper method to parse date
// private Date parseDate(String dateString) throws ParseException {
//     return new SimpleDateFormat("dd-MM-yyyy").parse(dateString);
// }

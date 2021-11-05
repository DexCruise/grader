import java.util.ArrayList;

public class StudentList implements java.io.Serializable {
    ArrayList<StudentRecord> students = new ArrayList<>();
    ArrayList<String> assignments = new ArrayList<>();


    StudentList() {

    }

    void AddStudent(int id, String name) {
        StudentRecord toAdd = new StudentRecord(id, name);
        for (String i : assignments) {
            toAdd.addScore(i, 0);
        }
        students.add(toAdd);
    }

    void AddAssignment(String title) {
        assignments.add(title);

        for (StudentRecord student : students) {
            student.scores.put(title, null);
        }
    }
}

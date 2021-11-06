import java.util.ArrayList;

public class StudentList implements java.io.Serializable {
    ArrayList<StudentRecord> students = new ArrayList<>();
    ArrayList<String> assignments = new ArrayList<>();


    StudentList() {

    }

    void addStudent(int id, String name) {
        StudentRecord toAdd = new StudentRecord(id, name);
        for (String i : assignments) {
            toAdd.addScore(i, 0);
        }
        students.add(toAdd);
    }

    void addAssignment(String title) {
        assignments.add(title);

        for (StudentRecord student : students) {
            student.scores.put(title, 0D);
        }
    }

    int getAssignmentCount() {
        return assignments.toArray().length;
    }

    int getStudentCount() {
        return students.toArray().length;
    }

    String getNameAtID(int id) {
        return students.get(id).name;
    }

    String getAssignmentAtID(int id) {
        return assignments.get(id);
    }

    int getStudentID(int index) {
        return students.get(index).getID();
    }

    double getStudentScore(int id, String title) {
        return students.get(id).scores.get(title);
    }
}

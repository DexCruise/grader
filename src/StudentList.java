import java.util.ArrayList;

public class StudentList implements java.io.Serializable {
    ArrayList<StudentRecord> students = new ArrayList<>();
    ArrayList<String> assignments = new ArrayList<>();


    StudentList() {

    }

    void addStudent(int id, String name) {
        StudentRecord toAdd = new StudentRecord(id, name);
        for (String i : assignments) {
            toAdd.setScore(i, 0);
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
        return students.get(id).getName();
    }

    String getAssignmentAtID(int id) {
        return assignments.get(id);
    }

    int getStudentID(int index) {
        return students.get(index).getID();
    }

    double getStudentScore(int id, String title) {
        return students.get(id).getScore(title);
    }

    void setID(int student_id, int id_to_set) {
        students.get(student_id).setID(id_to_set);
    }

    void setScore(int id, String title, double score) {
        students.get(id).setScore(title, score);
    }

    void setName(int id, String name) {
        students.get(id).setName(name);
    }

    void sort() {
        students.sort(StudentRecord::compare);
    }
}

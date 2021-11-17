import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashSet;

public class StudentList {
    final ArrayList<StudentRecord> students = new ArrayList<>();
    final ArrayList<String> assignments = new ArrayList<>();
    private int latestStudent = -1;

    StudentList() {

    }

    /*
    StudentList(File source) {
        // construct from a file
    }
     */

    void addStudent(int id, Name name) {
        StudentRecord toAdd = new StudentRecord(id, name);
        for (String i : assignments) {
            toAdd.setScore(i, 0);
        }
        students.add(toAdd);
        latestStudent = students.size() - 1;
    }

    void addAssignment(String title) {
        assignments.add(title);


        for (StudentRecord student : students) {
            student.scores.put(title, 0D);
        }
    }

    int getAssignmentCount() {
        return assignments.size();
    }

    int getStudentCount() {
        return students.size();
    }

    Name getNameAtID(int id) {
        return students.get(id).getName();
    }

    String getAssignmentAtID(int id) {
        if (id <= -1) {
            return assignments.get(assignments.size() + id);
        }
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

    void setName(int id, Name name) {
        students.get(id).setName(name);
    }

    void sort() {
        students.sort(StudentRecord::compare);
    }

    void cleanAssignmentList() {
        HashSet<String> assignmentSet = new HashSet<>(assignments);
        assignments.clear();
        assignments.addAll(assignmentSet);
    }

    Object[] getDataForNewestStudent() {
        ArrayList<Object> data = new ArrayList<>();

        StudentRecord latest = students.get(latestStudent);

        data.add(latest.id);
        data.add(latest.name);

        for (String i : assignments) {
            data.add(latest.getScore(i));
        }

        return data.toArray();
    }

    String escapeXML(String xml) {
        return xml.replaceAll("&", "&amp;")
                .replaceAll("\"", "&quot;")
                .replaceAll("'", "&apos;")
                .replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;");
    }

    private String serializeCompact() {
        cleanAssignmentList();

        StringBuilder text = new StringBuilder();

        text.append("<?XML version='1.0'?>");
        text.append("<export version='0.0.0'>");

        StringBuilder assignmentElement = new StringBuilder();
        // <assignments> <a>assignment title</a> </assignments>

        assignmentElement.append("<assignments>");
        for (String i : assignments) {
            assignmentElement.append("<a>");
            assignmentElement.append(escapeXML(i));
            assignmentElement.append("</a>");
        }
        assignmentElement.append("</assignments>");

        text.append(assignmentElement);

        StringBuilder studentElement = new StringBuilder();
        // <students> <student id='id' name='name'> <s>100<\s> </student> </student>

        studentElement.append("<students>");
        StringBuilder studentBuilder;
        for (StudentRecord i : students) {
            studentBuilder = new StringBuilder();
            studentBuilder.append("<student id='");
            studentBuilder.append(i.getID());
            studentBuilder.append("' name='");
            studentBuilder.append(escapeXML(i.getName().firstFirst()));
            studentBuilder.append("'>");
            for (String j : assignments) {
                studentBuilder.append("<s>");
                studentBuilder.append(i.getScore(j));
                studentBuilder.append("</s>");
            }
            studentBuilder.append("</student>");
            studentElement.append(studentBuilder);
        }
        studentElement.append("</students>");

        text.append(studentElement);
        text.append("</export>");

        return text.toString();
    }

    private String serialize() {
        cleanAssignmentList();

        StringBuilder text = new StringBuilder();

        text.append("<?XML version='1.0'?>\n");
        text.append("<export version='0.0.0'>\n");

        StringBuilder assignmentElement = new StringBuilder();
        // <assignments> <a>assignment title</a> </assignments>

        assignmentElement.append("  <assignments>");
        for (String i : assignments) {
            assignmentElement.append("\n    <a>");
            assignmentElement.append(escapeXML(i));
            assignmentElement.append("</a>");
        }
        assignmentElement.append("\n  </assignments>\n");

        text.append(assignmentElement);

        StringBuilder studentElement = new StringBuilder();
        // <students> <student id='id' name='name'> <s>100<\s> </student> </student>

        studentElement.append("  <students>\n");
        StringBuilder studentBuilder;
        for (StudentRecord i : students) {
            studentBuilder = new StringBuilder();
            studentBuilder.append("    <student id='");
            studentBuilder.append(i.getID());
            studentBuilder.append("' name='");
            studentBuilder.append(escapeXML(i.getName().firstFirst()));
            studentBuilder.append("'>\n      ");
            for (String j : assignments) {
                studentBuilder.append("<s>");
                studentBuilder.append(i.getScore(j));
                studentBuilder.append("</s>");
            }
            studentBuilder.append("\n    </student>");
            studentElement.append(studentBuilder);
            studentElement.append("\n");
        }
        studentElement.append("  </students>");

        text.append(studentElement);
        text.append("\n</export>");

        return text.toString();
    }
    void saveToFile(File file) throws java.io.IOException {

        FileWriter writer = new FileWriter(file);
        writer.write(this.serialize());

        writer.close();
    }
}

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("StudentRecords v0.0.0 by Dexter");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        StudentList students = new StudentList();

        students.addAssignment("Foo");
        students.addAssignment("Bar");
        students.addAssignment("Bif");
        students.addAssignment("Baz");
        students.addStudent(25340978, "Spam");
        students.addStudent(1, "Eggs");


        JPanel records = new JPanel();

        TableModel dataModel = new AbstractTableModel() {
            public int getColumnCount() { return students.getAssignmentCount() + 2; }
            public int getRowCount() { return students.getStudentCount(); }
            public boolean isCellEditable(int row, int col) { return true; }
            public void setValueAt(Object val, int row, int col) {
                if (col == 0) {
                    students.setID(row, Integer.parseInt(val.toString()));
                } else if (col == 1) {
                    students.setName(row, val.toString());
                } else {
                    students.setScore(row, students.getAssignmentAtID(col - 2), Double.parseDouble(val.toString()));
                }
            }
            public String getColumnName(int col) {
                if (col == 0) {
                    return "Student ID";
                } else if (col == 1) {
                    return "Student Name";
                } else {
                    return students.getAssignmentAtID(col - 2);
                }
            }
            public Object getValueAt(int row, int col) {
                if (col == 0) {
                    return students.getStudentID(row);
                }
                if (col == 1) {
                    return students.getNameAtID(row);
                } else {
                    return students.getStudentScore(row, students.getAssignmentAtID(col - 2));
                }
            }

        };

        JTable table = new JTable(dataModel);
        JScrollPane scrollPane = new JScrollPane(table);

        records.add(scrollPane);

        frame.add(records);
        frame.pack();
        frame.setVisible(true);
    }
}

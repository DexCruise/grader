import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.Random;

public class Main {
    private static final class NameDisplayStyle {
        public static final int FIRST_FIRST = 0, LAST_FIRST = 1;
    }

    private static void displayGUI(StudentList students) {
        JFrame frame = new JFrame("StudentRecords v0.0.0 by Dexter");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel records = new JPanel();
        JTable table = new JTable();

        int nameDisplayStyle = NameDisplayStyle.LAST_FIRST;
        TableModel dataModel = new AbstractTableModel() {
            public int getColumnCount() { return students.getAssignmentCount() + 2; }
            public int getRowCount() { return students.getStudentCount(); }
            public boolean isCellEditable(int row, int col) { return true; }
            public void setValueAt(Object val, int row, int col) {
                if (col == 0) {
                    students.setID(row, Integer.parseInt(val.toString()));
                    students.sort();
                    table.setModel(this);
                } else if (col == 1) {
                    students.setName(row, Name.parseName(val.toString()));
                    students.sort();
                    table.setModel(this);
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
                    //noinspection ConstantConditions
                    if (nameDisplayStyle == NameDisplayStyle.FIRST_FIRST) {
                        return students.getNameAtID(row).firstFirst();
                    }
                    //noinspection ConstantConditions
                    if (nameDisplayStyle == NameDisplayStyle.LAST_FIRST) {
                        return students.getNameAtID(row).lastFirst();
                    }
                    return "FAIL";
                } else {
                    return students.getStudentScore(row, students.getAssignmentAtID(col - 2));
                }
            }

        };

        JViewport viewport = new JViewport();
        viewport.setScrollMode(JViewport.SIMPLE_SCROLL_MODE);


        table.setFillsViewportHeight(true);
        table.setModel(dataModel);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane scrollPane = new JScrollPane(table);

        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        viewport.add(scrollPane);

        records.add(viewport);

        frame.add(records);
        frame.pack();
        frame.setVisible(true);
    }

    public static StudentList makeStudentListForTesting() {
        StudentList students = new StudentList();

        students.addAssignment("Foo");
        students.addAssignment("Bar");

        ArrayList<String> names = new ArrayList<>();
        names.add("Spam");
        names.add("Eggs");
        names.add("Coconuts");
        names.add("Cheems");
        names.add("Doge");
        names.add("Sus");
        names.add("Among");
        names.add("Us");
        names.add("This");
        names.add("And");
        names.add("That");
        names.add("Timothy");

        for (int i = 0; i < 100; i++) {
            students.addStudent(i,
                    new Name(
                            names.get(new Random().nextInt(names.size())),
                            names.get(new Random().nextInt(names.size()))
                    )
            );
        }

        students.sort();

        return students;
    }

    public static void main(String[] args) {
        StudentList students = makeStudentListForTesting();

        displayGUI(students);
    }
}

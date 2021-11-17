import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.io.File;

public class Main {

    private static final String ADD_STUDENT = "Add Student";
    private static final String DEL_STUDENT = "Remove Student";
    private static final String ADD_ASSIGNMENT = "Add Assignment";
    private static final String DEL_ASSIGNMENT = "Remove Assignment";

    private static final String SAVE_AS = "Save As";
    private static final String SAVE = "Save";
    private static final String OPEN = "Open";

    private static File currentFile = new File("test.xml");

    private static JTable studentTable;
    private static TableModel dataModel;
    private static StudentList students;

    private static final class NameDisplayStyle {
        public static final int FIRST_FIRST = 0, LAST_FIRST = 1;
    }

    private static JTable createStudentTable(StudentList students) {
        JTable table = new JTable();
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        int nameDisplayStyle = NameDisplayStyle.LAST_FIRST;
        dataModel = new AbstractTableModel() {
            public int getColumnCount() { return students.getAssignmentCount() + 2; }
            public int getRowCount() { return students.getStudentCount(); }
            public boolean isCellEditable(int row, int col) { return true; }
            public void setValueAt(Object val, int row, int col) {
                try {
                    if (col == 0) {
                        students.setID(row, Integer.parseInt(val.toString()));
                    } else if (col == 1) {
                        students.setName(row, Name.parseName(val.toString()));
                    } else {
                        students.setScore(row, students.getAssignmentAtID(col - 2), Double.parseDouble(val.toString()));
                    }
                    students.sort();
                } catch (java.lang.NumberFormatException e) {
                    System.out.println("Invalid String");
                }
                table.setModel(this);
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
                    throw new IllegalStateException("Illegal Name Display Style: " + nameDisplayStyle);
                } else {
                    return students.getStudentScore(row, students.getAssignmentAtID(col - 2));
                }
            }
        };


        table.setModel(dataModel);


        return table;
    }

    /**
     * Creates and displays a swing GUI of a table
     *
     * @param table table of data to display
     */

    private static void displayGUI(JTable table) {
        JFrame frame = new JFrame("StudentRecords v0.0.0 by Dexter");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JScrollPane scrollPane = new JScrollPane(table,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        scrollPane.setPreferredSize(new Dimension(400, 200));

        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(10);

        HomeMenuButtonListener homeMenuButtonListener = new HomeMenuButtonListener();

        JPanel buttonPanel = new JPanel();

        //make four buttons for managing things
        JButton addAssignmentButton = new JButton("Add Assignment");
        JButton addStudentButton = new JButton("Add Student");
        JButton deleteAssignmentButton = new JButton("Delete Assignment");
        JButton deleteStudentButton = new JButton("Delete Student");


        //set the commands that each of the buttons send
        addAssignmentButton.setActionCommand(ADD_ASSIGNMENT);
        addStudentButton.setActionCommand(ADD_STUDENT);
        deleteAssignmentButton.setActionCommand(DEL_ASSIGNMENT);
        deleteStudentButton.setActionCommand(DEL_STUDENT);


        //nicely ask each button to work
        addAssignmentButton.addActionListener(homeMenuButtonListener);
        addStudentButton.addActionListener(homeMenuButtonListener);
        deleteAssignmentButton.addActionListener(homeMenuButtonListener);
        deleteStudentButton.addActionListener(homeMenuButtonListener);

        buttonPanel.add(addAssignmentButton);
        buttonPanel.add(deleteAssignmentButton);
        buttonPanel.add(addStudentButton);
        buttonPanel.add(deleteStudentButton);

        buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
        buttonPanel.setMinimumSize(new Dimension(200, 20));

        JTabbedPane ribbon = new JTabbedPane(JTabbedPane.TOP);

        FileMenuButtonListener fileMenuButtonListener = new FileMenuButtonListener();

        JPanel filePanel = new JPanel();

        filePanel.add(new JLabel("File Menu Incomplete"));

        JButton saveButton = new JButton("Save");
        JButton saveAsButton = new JButton("Save As");
        JButton openButton = new JButton("Open");

        saveButton.setActionCommand(SAVE);
        saveAsButton.setActionCommand(SAVE_AS);
        openButton.setActionCommand(OPEN);

        saveButton.addActionListener(fileMenuButtonListener);
        saveAsButton.addActionListener(fileMenuButtonListener);
        openButton.addActionListener(fileMenuButtonListener);

        filePanel.add(saveButton);
        filePanel.add(saveAsButton);
        filePanel.add(openButton);

        ribbon.addTab("File", filePanel);
        ribbon.addTab("Home", buttonPanel);

        frame.add(ribbon, BorderLayout.BEFORE_FIRST_LINE);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.pack();

        frame.setVisible(true);
    }

    /**
     * @param StudentCount number of students to generate
     * @param AssignmentCount number of assignments to generate
     *
     * @return returns a randomly generated StudentList
     */

    public static StudentList makeStudentListForTesting(int StudentCount, int AssignmentCount) {
        students = new StudentList();

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

        ArrayList<String> assignments = new ArrayList<>();

        assignments.add("Foo");
        assignments.add("Bar");
        assignments.add("Homework");
        assignments.add("Project");
        assignments.add("Research");
        assignments.add("Paper");
        assignments.add("Essay");


        for (int i = 0; i < StudentCount; i++) students.addStudent(i,
                    new Name(names.get(new Random().nextInt(names.size())),
                            names.get(new Random().nextInt(names.size()))));


        for (int i = 0; i < AssignmentCount; i++)
            students.addAssignment(assignments.get(new Random().nextInt(assignments.size())));

        students.sort();
        return students;
    }

    public static void main(String[] args) {
        StudentList students = makeStudentListForTesting(30000, 1000);

        studentTable = createStudentTable(students);

        displayGUI(studentTable);

    }

    private static class HomeMenuButtonListener implements ActionListener {
        public void actionPerformed(final ActionEvent ev) {
            switch (ev.getActionCommand()) {
                case ADD_STUDENT -> students.addStudent(-1, new Name("Student", "Name"));
                case ADD_ASSIGNMENT -> students.addAssignment("Assignment " + students.getAssignmentCount());
//                case DEL_ASSIGNMENT -> System.out.println("No Deleting Yet!");
//                case DEL_STUDENT -> System.out.println("No Deleting Yet!");
            }

            studentTable.setModel(dataModel);
        }
    }

    private static class FileMenuButtonListener implements ActionListener {
        public void actionPerformed(final ActionEvent ev) {
            try {
                switch (ev.getActionCommand()) {
                    case SAVE -> students.saveToFile(currentFile);
//                    case SAVE_AS -> students.addAssignment("Assignment " + students.getAssignmentCount());
                    case OPEN -> System.out.println("not implemented!");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            studentTable.setModel(dataModel);
        }
    }
}

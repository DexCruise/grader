import javax.swing.*;
import javax.swing.table.TableColumn;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("StudentRecords by Dexter v0.0.0");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel records = new JPanel();

        JTable recordTable = new JTable();
        recordTable.addColumn(new TableColumn(0));
        recordTable.addColumn(new TableColumn(1));
        recordTable.setRowHeight(10);
        recordTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        records.add(recordTable);

        frame.add(recordTable);

        // System.out.printf("Tim's homework from yesterday: %f%n", Tim.getScore("yesterday's homework"));
        frame.pack();
        frame.setVisible(true);
    }
}

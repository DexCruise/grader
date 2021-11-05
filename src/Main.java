import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("StudentRecords by Dexter v0.0.0");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel records = new JPanel();
        JTable recordTable = new JTable();


        // System.out.printf("Tim's homework from yesterday: %f%n", Tim.getScore("yesterday's homework"));
        frame.pack();
        frame.setVisible(true);
    }
}

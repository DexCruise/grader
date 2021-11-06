import java.util.HashMap;

public class StudentRecord implements java.io.Serializable {
    public int id;
    public String name;
    public HashMap<String, Double> scores = new HashMap<>();

    StudentRecord(int start_id, String start_name) {
        id = start_id;
        name = start_name;

    }

    void addScore(String title, double score) {
        scores.put(title, score);
    }

    double getScore(String title) {
        return scores.get(title);
    }

    int getID() {
        return id;
    }
}
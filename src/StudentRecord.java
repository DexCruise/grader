import java.util.TreeMap;

public class StudentRecord implements java.io.Serializable {
    public int id;
    public String name;
    public TreeMap<String, Double> scores = new TreeMap<>();


    StudentRecord(int start_id, String start_name) {
        id = start_id;
        name = start_name;

    }

    static int compare(StudentRecord o1, StudentRecord o2) {
        return Integer.compare(o1.getID(), o2.getID());
    }

    void setScore(String title, double score) {
        scores.put(title, score);
    }

    double getScore(String title) {
        return scores.get(title);
    }

    void setID(int id_to_set) {
        id = id_to_set;
    }

    int getID() {
        return id;
    }

    void setName(String nameToSet) {
        name = nameToSet;
    }

    String getName() {
        return name;
    }
}
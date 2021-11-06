public class Name {
    final String first, last;

    Name(String firstName, String lastName) {
        first = firstName;
        last = lastName;
    }

    public static int compare(Name name, Name name1) {
        int lastCmp = String.CASE_INSENSITIVE_ORDER.compare(name.last, name1.last);
        if (lastCmp != 0) {
            return lastCmp;
        } else {
            return String.CASE_INSENSITIVE_ORDER.compare(name.first, name.last);
        }
    }


    public String firstFirst() {
        return first + " " + last;
    }

    public String lastFirst() {
        return last + ", " + first;
    }

    public static Name parseName(String name) {
        String[] words = name.split(" ");
        return new Name(words[0], words[words.length - 1]);
    }
}

package wordsGeneric;

import java.util.HashMap;

public class WordPair {

    private String first, second;

    public WordPair(String first, String second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public String toString() {
        return "<" + first + ", " + second + ">";
    }

    @Override
    public int hashCode() {
        return first.hashCode() + second.hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        WordPair wp = (WordPair) obj;
        String first2 = wp.getFirst();
        String second2 = wp.getSecond();

        return first.equals(first2) && second.equals(second2);
    }

    public String getFirst() {
        return first;
    }

    public String getSecond() {
        return second;
    }

    public static void main(String[] args) {
        WordPair wp1 = new WordPair("first", "second");
        WordPair wp2 = new WordPair("computer", "science");
        WordPair wp3 = new WordPair("is", "cool");

        WordPair wp4 = new WordPair("computer", "science");

        HashMap<WordPair, String> hm = new HashMap<WordPair, String>();
        hm.put(wp2, "hey");
        hm.put(wp3, "hello");

        System.out.println(wp1.equals(wp4));
        System.out.println(wp2.equals(wp4));
        System.out.println(hm.get(wp2));
        System.out.println(hm.get(new WordPair("is", "cool")));
    }
}
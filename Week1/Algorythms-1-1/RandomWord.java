import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        int i = 1;
        String champion = "";
        while (!StdIn.isEmpty()) {
            Double p = 1.0 / i;
            String word = StdIn.readString();
            if (StdRandom.bernoulli(p)) {
                champion = word;
            }
            i++;
        }
        StdOut.println(champion);
    }
}

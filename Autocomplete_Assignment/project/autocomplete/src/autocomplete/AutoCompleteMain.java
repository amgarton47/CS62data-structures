package autocomplete;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AutoCompleteMain {

    public static void main(String[] args) {
        int numTerms = Integer.parseInt(args[0]);
        File data = new File("../" + args[1]);

        List<Term> aList = new ArrayList<Term>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(data));
            int numLines = Integer.parseInt(br.readLine());

            for (int i = 0; i < numLines; i++) {
                String[] line = br.readLine().trim().split("\t");

                Term term = new Term(line[1], Long.parseLong(line[0]));
                aList.add(term);
            }

            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter a new prefix: ");
        String query = sc.nextLine();

        while (!query.equals("exit")) {
            Autocomplete auto = new Autocomplete(aList);
            List<Term> l = auto.allMatches(query);

            if (l.size() == 0) {
                System.out.println("There are no matches for that query.");
            } else {

                int t = Math.min(numTerms, l.size());

                System.out.println("There are " + l.size() + " matches.");
                System.out.println("The " + t + " largest are:");

                for (int i = 0; i < t; i++) {
                    System.out.println(l.get(i));
                }
            }

            System.out.println("Enter a new prefix, or type 'exit': ");
            query = sc.nextLine();
        }

        sc.close();
    }

}

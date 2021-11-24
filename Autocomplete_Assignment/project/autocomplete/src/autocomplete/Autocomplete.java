package autocomplete;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Autocomplete implements AutocompleteInterface {
    List<Term> list;

    Autocomplete(List<Term> list) {
        this.list = list;
        Collections.sort(this.list);
    }

    @Override
    public List<Term> allMatches(String prefix) {
        List<Term> matches = new ArrayList<Term>();

        int first = BinarySearchForAll.firstIndexOf(list, new Term(prefix, 0), Term.byPrefixOrder(prefix.length()));

        int last = BinarySearchForAll.lastIndexOf(list, new Term(prefix, 0), Term.byPrefixOrder(prefix.length()));

        if (first == -1 || last == -1) {
            return matches;
        }

        for (int i = first; i <= last; i++) {
            matches.add(list.get(i));
        }

        Collections.sort(matches, Term.byReverseWeightOrder());

        return matches;

    }

}

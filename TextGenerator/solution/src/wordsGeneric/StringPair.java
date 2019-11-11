/**
 * Simple class to represent a pair of Strings
 *
 */
package wordsGeneric;

public class StringPair {
    // two items in pair
    private final String first;
    private final String second;
    
    /**
     * Create pair from fst and snd
     * @param fst  first element of pair
     * @param snd  second element of pair
     */
    public StringPair(String fst,String snd) {
        first = fst;
        second = snd;
    }
    
    /**
     * @return first item of pair
     */
    public String getFirst() {
        return first;
    }
    
    /**
     * @return second item of pair
     */
    public String getSecond() {
        return second;
    }
    
    /**
     * @param other  another pair to be compared with this one
     * @return true iff both items in this pair are equal to the corresponding items in other
     */
    public boolean equals(Object other) {
        if (other instanceof StringPair) {
        	StringPair otherPair = (StringPair)other;
            return this.first.equals(otherPair.getFirst()) && this.second.equals(otherPair.getSecond());
        } else {
            return false;
        }
    }
    
    public int hashCode() {
    	return this.first.hashCode()^this.second.hashCode();
    }
    
    /**
     * @return readable representation of pair
     */
    public String toString() {
        return "<"+first+","+second+">";
    }
}

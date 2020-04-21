/**
 * Simple class to represent a pair of Strings
 *
 */
package wordsGeneric;

import java.util.Objects;

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
    public boolean equals(Object y) {
    	if(y==this)
    		return true;
    	if(y == null)
    		return false;
    	if(y.getClass()!= this.getClass())
    		return false;
    	StringPair other = (StringPair) y;
        return this.first.equals(other.getFirst()) && this.second.equals(other.getSecond());
        
    }
    
    public int hashCode() {
    	return Objects.hash(first, second);    
    }
    
    /**
     * @return readable representation of pair
     */
    public String toString() {
        return "<"+first+","+second+">";
    }
}

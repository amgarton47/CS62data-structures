package searchengine;
import java.util.ArrayList;

/**
 * an implementation of postings list
 * your implementation must use a singly linked list for efficiency
 * 
 * @author dkauchak
 *
 */
public class PostingsList{	
	/**
	 * add a document ID to the END of this posting list
	 *
	 * @param docID the docID of the document being added
	 */
	public void addDoc(int docID){
	}
		
	/**
	 * @return the number of docIDs for this posting list
	 */
	public int size(){
		return -1;
	}
	
	/**
	 * From the linked list structure, generate an integer array containing 
	 * all of the document ids.  This will make our life easy when we want to 
	 * print out the ids.
	 * 
	 * @return
	 */
	public ArrayList<Integer> getIDs(){
		return null;
	}
	
	/**
	 * Given two postings lists, return a new postings list that contains the AND
	 * of the postings, i.e. all the docIDs that occur in both posting1 and posting2.
	 * Note both postings lists are assumed to be stored in increasing order by docID.
	 * 
	 * @param posting1
	 * @param posting2
	 * @return the AND of the postings lists
	 */
	public static PostingsList andMerge(PostingsList posting1, PostingsList posting2){
		return null;
	}
	
	/**
	 * Given two postings lists, return a new postings list that contains the OR
	 * of the postings, i.e. all those docIDs that occur in either posting1 and posting2
	 * 
	 * @param posting1
	 * @param posting2
	 * @return the OR of the postings lists
	 */
	public static PostingsList orMerge(PostingsList posting1, PostingsList posting2){
		return null;
	}
}

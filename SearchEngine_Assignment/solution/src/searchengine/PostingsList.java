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
	private PostingsListNode head;
	private PostingsListNode tail;
	private int size = 0;
	
	/**
	 * add a document ID to the END of this posting list
	 *
	 * @param docID the docID of the document being added
	 */
	public void addDoc(int docID){
		if( head == null ){
			head = new PostingsListNode(docID);
			tail = head;
		}else{
			if( tail.value() != docID ){
				tail.setNext(new PostingsListNode(docID));
				tail = tail.next();
			}
		}
		
		size++;
	}
		
	/**
	 * @return the number of docIDs for this posting list
	 */
	public int size(){
		return size;
	}
	
	/**
	 * From the linked list structure, generate an integer array containing 
	 * all of the document ids.  This will make our life easy when we want to 
	 * print out the ids.
	 * 
	 * @return
	 */
	public ArrayList<Integer> getIDs(){
		PostingsListNode finger = head;
		ArrayList<Integer> returnMe = new ArrayList<Integer>();
		
		while( finger != null ){
			returnMe.add(finger.value());
			finger = finger.next();
		}
		
		return returnMe;
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
		PostingsListNode node1 = posting1.head;
		PostingsListNode node2 = posting2.head;
		
		PostingsList result = new PostingsList();
		
		while( node1 != null && node2 != null ){
			if( node1.value() == node2.value() ){
				result.addDoc(node1.value());
				node1 = node1.next();
				node2 = node2.next();
			}else if( node1.value() < node2.value() ){
				node1 = node1.next();
			}else{
				node2 = node2.next();
			}
		}
		
		return result;
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
		PostingsListNode node1 = posting1.head;
		PostingsListNode node2 = posting2.head;
		
		PostingsList result = new PostingsList();
		
		while( node1 != null && node2 != null ){
			if( node1.value() == node2.value() ){
				result.addDoc(node1.value());
				node1 = node1.next();
				node2 = node2.next();
			}else if( node1.value() < node2.value() ){
				result.addDoc(node1.value());
				node1 = node1.next();
			}else{
				result.addDoc(node2.value());
				node2 = node2.next();

			}
		}
		
		while( node1 != null ){
			result.addDoc(node1.value());
			node1 = node1.next();
		}
		
		while( node2 != null ){
			result.addDoc(node2.value());
			node2 = node2.next();
		}
		
		return result;
	}
}

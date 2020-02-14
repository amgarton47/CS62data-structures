package searchengine;
import java.util.ArrayList;

/**
 * An inverted index that uses two ArrayLists to store the mapping
 * from word to postings list
 * 
 * @author dkauchak
 *
 */
public class Index {
	private ArrayList<String> words = new ArrayList<String>();
	private ArrayList<PostingsList> lists = new ArrayList<PostingsList>();
	
	/**
	 * Add an occurrence of word in docID to the index
	 * 
	 * @param word
	 * @param docID
	 */
	public void addOccurrence(String word, int docID){
		int index = words.indexOf(word);
		
		if( index == -1 ){
			// we didn't find it so add it and create a new postings list
			words.add(word);
			lists.add(new PostingsList());
			index = words.size()-1;
		}
		
		lists.get(index).addDoc(docID);
	}
	
	/**
	 * Get the postings list associated with this word in the inverted index
	 * 
	 * @param word
	 * @return
	 */
	public PostingsList getPostingsList(String word){
		int index = words.indexOf(word);
		
		if( index != -1 ){
			return lists.get(index);
		}else{
			return new PostingsList();
		}
	}	
}
package searchengine;
import java.util.ArrayList;

/**
 * A representation of the quotes that will be stored in our
 * data collection.
 * 
 * @author dkauchak
 */
public class Quote{
	public static final int LINE_LENGTH = 50;
	
	private int docID;
	private ArrayList<String> text;
	private String author;
	
	/**
	 * Create a new document
	 * 
	 * @param docID the unique ID associated with this document
	 * @param text the words in the document
	 */
	public Quote(int docID, ArrayList<String> text, String author){
		this.docID = docID;
		this.text = text;
		this.author = author;
	}

	
	/**
	 * Get the document ID
	 * 
	 * @return the docID
	 */
	public int getDocID() {
		return docID;
	}

	/**
	 * Set the document ID
	 * 
	 * @param docID
	 */
	public void setDocID(int docID) {
		this.docID = docID;
	}

	/**
	 * Get the text (i.e. words) that make up this document.
	 * 
	 * @return
	 */
	public ArrayList<String> getText() {
		return text;
	}
	
	/**
	 * Set the text (i.e. words) that make up this document.
	 * 
	 * @param text
	 */
	public void setText(ArrayList<String> text) {
		this.text = text;
	}
	
	/**
	 * @return the author of this quote
	 */
	public String getAuthor(){
		return author;
	}
	
	public String toString(){
		StringBuilder returnMe = new StringBuilder();
		
		for( int i = 0; i < LINE_LENGTH; i++ ){
			returnMe.append("-");
		}
		
		//returnMe.append("\nDocID: ");
		//returnMe.append(docID);
		returnMe.append("\n");
		
		int line_char_count = 0;
		
		for( String s: text ){
			returnMe.append(s);
		
			line_char_count += s.length();
			
			if( line_char_count >= LINE_LENGTH ){
				returnMe.append("\n");
				line_char_count = 0;
			}else{
				returnMe.append(" ");
				line_char_count++;
			}
		}
		
		returnMe.append("\n  --" + author);
		
		return returnMe.toString();
	}
}

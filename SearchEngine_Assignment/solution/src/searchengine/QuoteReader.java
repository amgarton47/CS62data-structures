package searchengine;
import java.io.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ArrayList;

/**
 * A document reader for the collection of quotes.
 * 
 * @author dkauchak
 * 
 */
public class QuoteReader implements Iterator<Quote>{
	private BufferedReader in;
	private String nextLine;
	private int nextDocID = 0;
	
	public QuoteReader(String documentFile){
		try{
			in = new BufferedReader(new FileReader(documentFile));
			nextLine = in.readLine();
		}catch(IOException e){
			throw new RuntimeException("Problems opening file: " + documentFile + "\n" + e.toString());
		}
	}

	/**
	 * Are there more documents to be read?
	 */
	public boolean hasNext() {
		return nextLine != null;
	}

	/**
	 * Get the next document.
	 */
	public Quote next(){
		if( !hasNext() ){
			throw new NoSuchElementException();
		}
		
		String[] parts = nextLine.split("\t");
		String quote = parts[0];
		String author = parts[1];
		
		ArrayList<String> tokens = tokenize(quote);
				
		Quote returnMe = new Quote(nextDocID, tokens, author);
		nextDocID++;
		
		try{
			nextLine = in.readLine();
		}catch(IOException e){
			throw new RuntimeException("Problems reading file\n" + e.toString());
		}
		
		return returnMe;
	}
	
	/**
	 * Breaks the input text into words based only on whitespace. 
	 */
	private ArrayList<String> tokenize(String text) {
		return new ArrayList<String>(Arrays.asList(text.split("\\s+")));
	}
	
	public void remove() {
		// method is optional
	}
}
package searchengine;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Class representing a basic search engine for performing AND
 * queries on quote data
 * 
 * @author dkauchak
 *
 */
public class SearchEngine {
	// the individual quotes so we can print them out
	private ArrayList<Quote> quotes = new ArrayList<Quote>(); 
	private Index index = new Index();
	
	/**
	 * Generate a new search engine and load the inverted index
	 * 
	 * @param quotesFile a file of quotes of the form quote<tab>author
	 */
	public SearchEngine(String quotesFile){
		QuoteReader reader = new QuoteReader(quotesFile);

		System.out.println("Loading quotes...");

		while( reader.hasNext() ){
			Quote q = reader.next();

			for( String word: q.getText() ){
				index.addOccurrence(word, q.getDocID());
			}

			quotes.add(q);

			if( (q.getDocID()+1) % 1000 == 0 ){
				System.out.println(q.getDocID()+1);
			}
		}

		System.out.println("Index generated!");
		System.out.println();
	}

	/**
	 * Run the interactive shell that allows the user to query the index
	 * and get back the printed results.
	 */
	public void run(){
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		String query;

		try {
			query = promptUser(in);

			while( !query.equals("") ){
				processQuery(query);

				System.out.println();
				System.out.println();
				query = promptUser(in);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	private void processQuery(String query){
		String[] queryWords = query.toLowerCase().split("\\s+");

		PostingsList working = index.getPostingsList(queryWords[0]);

		int i = 1;
		
		while( i < queryWords.length && working.size() > 0 ){
			working = PostingsList.andMerge(working, index.getPostingsList(queryWords[i]));
			i++;
		}
		
		// print out the results
		ArrayList<Integer> ids = working.getIDs();

		if( ids.size() == 0 ){
			System.out.println("No documents had all those words");
		}else{
			for( Integer id: ids ){
				System.out.println(quotes.get(id));
			}
		}
	}

	/**
	 * Prompt the user to enter a query from the stream in
	 * 
	 * @param in
	 * @return the user's query
	 * @throws IOException
	 */
	private String promptUser(BufferedReader in) throws IOException{
		System.out.print("Enter a query (blank to exit): ");
		return in.readLine();
	}

	public static void main(String[] args) {
		String quotesFile = "/Users/dkauchak/classes/cs201/assignments/assignment6/part2/starter/quotes50K.txt";

		SearchEngine search = new SearchEngine(quotesFile);
		search.run();
	}
}

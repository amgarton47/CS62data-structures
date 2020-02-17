package searchengine;
/**
 * A linked list node
 * 
 * @author extracted from Duane Bailey's structure5 library
 *
 * @param <E> the type of data to be stored in the node
 */
public class PostingsListNode { 
	private int data; 
	private PostingsListNode next; 
	
	public PostingsListNode(int data, PostingsListNode next) { 
		this.data = data; 
		this.next = next; 
	} 
	
	public PostingsListNode(int v) { 
		this(v,null); 
	}
	
	public PostingsListNode next() { 
		return next; 
	} 
	
	public void setNext(PostingsListNode next) { 
		this.next = next; 
	} 
	
	public int value() { 
		return data; 
	} 
	
	public void setValue(int value) { 
		data = value; 
	} 
	
	public String toString() { 
		return "<Node: "+value()+">"; 
	} 
}
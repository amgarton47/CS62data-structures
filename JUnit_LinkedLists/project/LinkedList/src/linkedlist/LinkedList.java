package linkedlist;

/**
 * A partial linked list implementation
 * 
 * @author dave
 *
 * @param <E>
 */
public class LinkedList<E>{ 
	private Node head; 

	public LinkedList() { 
		head = null; 
	} 
	
	public void addFirst(E value) {
		Node newNode = new Node(value);
		newNode.setNext(head);
		head = newNode;
	}
		
	public E removeFirst() {
		if( head == null ) {
			return null;
		} else {
			E returnMe = head.value(); 
			head = head.next(); // move head down the list 
			return returnMe;
		} 
	}

	public E getFirst() {
		return head == null ? null : head.value(); 
	}
			
	public void addLast(E value){
		if( head == null ){
			head = new Node(value);
		}else{
			Node finger = head;
			
			while( finger.next() != null ){
				finger = finger.next();
			}
			
			finger.setNext(new Node(value));
		}
	}
	
	public E get(int index){
		Node finger = head;
		
		for( int i = 0; i < index && finger != null; i++ ){
			finger = finger.next();
		}
		
		return finger == null ? null : finger.value();
	}
	
	public boolean contains(E value){ 
		Node finger = head; 

		while (finger != null && !finger.value().equals(value)) { 
			finger = finger.next(); 
		}
		
		return finger != null; 
	}

	private class Node{
		private E data; 
		private Node next; 
		
		public Node(E v, Node next) { 
			data = v; 
			this.next = next; 
		} 
		
		public Node(E v) { 
			this(v,null); 
		}
		
		public Node next() { 
			return next;
		} 
		
		public void setNext(Node next) { 
			this.next = next; 
		} 
		
		public E value() { 
			return data; 
		} 
		
		public void setValue(E value) { 
			data = value; 
		} 
		
		public String toString() { 
			return "<Node: "+value()+">"; 
		} 
	}
}
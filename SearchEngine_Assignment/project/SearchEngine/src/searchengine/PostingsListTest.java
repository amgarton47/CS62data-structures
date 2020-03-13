package searchengine;
//import static org.junit.Assert.*;
//import java.util.ArrayList;
//import org.junit.Test;


public class PostingsListTest {

	/*@Test
	public void testEmpty() {
		PostingsList list = new PostingsList();

		assertEquals(list.getIDs(), new ArrayList<Integer>());
	}
	
	@Test
	public void testSize() {
		PostingsList list = new PostingsList();

		list.addDoc(0);
		list.addDoc(1);
		list.addDoc(2);

		assertEquals(list.size(), 3);
	}

	@Test
	public void testAdd1() {
		// NOTE: Without additional functions, the only way to test the addDoc method is with
		// the getIDs method.  So, if this doesn't work, you could have an error in EITHER of
		// these methods.
		PostingsList list = new PostingsList();

		list.addDoc(0);

		ArrayList<Integer> answer = new ArrayList<Integer>();
		answer.add(0);

		assertEquals(list.getIDs(), answer);
	}

	@Test
	public void testAddMultiple() {
		// NOTE: Without additional functions, the only way to test the addDoc method is with
		// the getIDs method.  So, if this doesn't work, you could have an error in EITHER of
		// these methods.
		PostingsList list = new PostingsList();

		list.addDoc(0);
		list.addDoc(1);
		list.addDoc(2);

		ArrayList<Integer> answer = new ArrayList<Integer>();
		answer.add(0);
		answer.add(1);
		answer.add(2);

		assertEquals(list.getIDs(), answer);
	}
	
	@Test
	public void testAndMerge1() {
		PostingsList list1 = new PostingsList();
		PostingsList list2 = new PostingsList();
		
		list1.addDoc(0);
		list2.addDoc(0);
		
		PostingsList result = PostingsList.andMerge(list1, list2);

		ArrayList<Integer> answer = new ArrayList<Integer>();
		answer.add(0);

		assertEquals(result.getIDs(), answer);
	}
	
	@Test
	public void testAndMerge2() {
		PostingsList list1 = new PostingsList();
		PostingsList list2 = new PostingsList();
		
		list1.addDoc(0);
		list1.addDoc(2);
		list2.addDoc(1);
		
		PostingsList result = PostingsList.andMerge(list1, list2);

		ArrayList<Integer> answer = new ArrayList<Integer>();

		assertEquals(result.getIDs(), answer);
	}
	
	@Test
	public void testAndMerge3() {
		PostingsList list1 = new PostingsList();
		PostingsList list2 = new PostingsList();
		
		list1.addDoc(0);
		list1.addDoc(2);
		list1.addDoc(4);
		
		list2.addDoc(1);
		list2.addDoc(2);
		
		PostingsList result = PostingsList.andMerge(list1, list2);

		ArrayList<Integer> answer = new ArrayList<Integer>();
		answer.add(2);

		assertEquals(result.getIDs(), answer);
	}
	
	@Test
	public void testAndMerge4() {
		PostingsList list1 = new PostingsList();
		PostingsList list2 = new PostingsList();
		
		list1.addDoc(0);
		list1.addDoc(2);
		list1.addDoc(4);
		
		list2.addDoc(1);
		list2.addDoc(2);
		list2.addDoc(4);
		
		PostingsList result = PostingsList.andMerge(list1, list2);

		ArrayList<Integer> answer = new ArrayList<Integer>();
		answer.add(2);
		answer.add(4);

		assertEquals(result.getIDs(), answer);
	}
	
	@Test
	public void testOrMerge1() {
		PostingsList list1 = new PostingsList();
		PostingsList list2 = new PostingsList();
		
		list1.addDoc(0);
		list2.addDoc(0);
		
		PostingsList result = PostingsList.orMerge(list1, list2);

		ArrayList<Integer> answer = new ArrayList<Integer>();
		answer.add(0);

		assertEquals(result.getIDs(), answer);
	}
	
	@Test
	public void testOrMerge2() {
		PostingsList list1 = new PostingsList();
		PostingsList list2 = new PostingsList();
		
		list1.addDoc(0);
		list1.addDoc(2);
		list2.addDoc(1);
		
		PostingsList result = PostingsList.orMerge(list1, list2);

		ArrayList<Integer> answer = new ArrayList<Integer>();
		answer.add(0);
		answer.add(1);
		answer.add(2);

		assertEquals(result.getIDs(), answer);
	}
	
	@Test
	public void testOrMerge3() {
		PostingsList list1 = new PostingsList();
		PostingsList list2 = new PostingsList();
		
		list1.addDoc(0);
		list1.addDoc(2);
		list1.addDoc(4);
		
		list2.addDoc(1);
		list2.addDoc(2);
		
		PostingsList result = PostingsList.orMerge(list1, list2);

		ArrayList<Integer> answer = new ArrayList<Integer>();
		answer.add(0);
		answer.add(1);
		answer.add(2);
		answer.add(4);
		
		assertEquals(result.getIDs(), answer);
	}
	
	@Test
	public void testOrMerge4() {
		PostingsList list1 = new PostingsList();
		PostingsList list2 = new PostingsList();
		
		list1.addDoc(0);
		list1.addDoc(2);
		list1.addDoc(4);
		
		list2.addDoc(1);
		list2.addDoc(2);
		list2.addDoc(4);
		
		PostingsList result = PostingsList.orMerge(list1, list2);

		ArrayList<Integer> answer = new ArrayList<Integer>();
		answer.add(0);
		answer.add(1);
		answer.add(2);
		answer.add(4);

		assertEquals(result.getIDs(), answer);
	}*/
}

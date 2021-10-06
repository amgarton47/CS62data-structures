import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * This is a demonstration of a test case in a JUnit test suite
 * for operations within the ArrayList class.
 *  
 * @author markk
 */

public class ArrayListTest {

	/**
	 * Assertion: ArrayList.isEmpty(empty_list) returns true
	 * 			  for an empty ArrayList, and false otherwise.
	 */
	@Test
	public void test_isEmpty() {
		// create an empty list, expect True
		ArrayList<String> testList = new ArrayList<>();
		assertTrue(testList.isEmpty(), "list starts out empty");
		
		// add something to the list, expect False
		testList.add("a string");
		assertFalse(testList.isEmpty(), "after .add it is no longer empty");
	}
}

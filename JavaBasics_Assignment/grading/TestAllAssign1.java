import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

class TestAllAssign1 {
		
	Card card1 = new Card(3, "hearts");
	Card card2 = new Card(11, "spades");
	Card card3 = new Card(12, "clubs");
	Card card4 = new Card(13, "diamonds");
	
	Person p1 = new Person("Bob", "TheBuilder");

	@Test
	void testGetNum() {
		assertEquals(card1.getNumber(), "3");
		assertEquals(card2.getNumber(), "Jack");
		assertEquals(card3.getNumber(), "Queen");
		assertEquals(card4.getNumber(), "King");
	}
	
	@Test
	void testGetSuit() {
		assertEquals(card1.getSuit(), "hearts");
		assertEquals(card2.getSuit(), "spades");
		assertEquals(card3.getSuit(), "clubs");
		assertEquals(card4.getSuit(), "diamonds");
	}
	
	@Test
	void testFlip() {
		assertFalse(card1.isFaceUp());
		card1.flip();
		assertTrue(card1.isFaceUp());
	}
	
	@Test
	void testPerson() {
		assertEquals(p1.getFirst(), "Bob");
		assertEquals(p1.getLast(), "TheBuilder");
		assertEquals(p1.getAge(), 18);
		p1.anotherYear();
		assertEquals(p1.getAge(), 19);

	}
	
	@Test
	void testWarmUp() {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		assertEquals(1100, WarmUp.savings(1000, .1));
		assertEquals(1331, WarmUp.savings(1000, .1, 3));

		String expectedOutput = "a\n \nb\n3\n10\n5\n16\n8\n4\n2\n1\n";
		WarmUp.printVertical("a b");
		WarmUp.hailstorm(3);
		assertEquals(expectedOutput, outContent.toString());
	}
	

}

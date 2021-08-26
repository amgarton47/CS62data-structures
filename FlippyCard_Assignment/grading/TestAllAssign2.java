package gradingflippycard;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestAllAssign2 {
	
	//WARMUP TESTS
	
	@Test
	void testCountHearts() {
		Card card1 = new Card(2, "hearts");
		Card card2 = new Card(11, "diamonds");
		Card card3 = new Card(12, "clubs");
		Card card4 = new Card(3, "hearts");
		
		//test with 1 heart and 2 non-hearts
		Card[] test1 = new Card[3];
		test1[0] = card1;
		test1[1] = card2;
		test1[2] = card3;
		assertEquals(WarmUp.countHearts(test1), 1);
		
		//test that result changes after a new heart card has been added
		Card[] test2 = new Card[4];
		for (int i = 0; i < 3; i++) {
			test2[i] = test1[i];
		}
		test2[3] = card4;
		assertEquals(WarmUp.countHearts(test2), 2);
		
		//test empty list
		Card[] test3 = {};
		assertEquals(WarmUp.countHearts(test3), 0);
	}
	
	@Test
	void testAddArraysSameLength() {
		double[] array1 = {1, 2, 3};
		double[] array2 = {2, 10, -3};
		
		double[] expectedResult = {3, 12, 0};
		double[] actualResult = WarmUp.addArraysSameLength(array1, array2);
		
		for (int i = 0; i < 3; i++) {
			assertEquals(expectedResult[i], actualResult[i]);
		}
		
	}

	@Test
	void testReverseArray() {
		//even length case
		String[] test1 = {"1", "2", "3", "4"};
		String[] expectedresult1 = {"4", "3", "2", "1"};
		
		//odd length case
		String[] test2 = {"1", "2", "3", "4", "5"};
		String[] expectedresult2 = {"5", "4", "3", "2", "1"};
		
		String[][] tests = {test1, test2};
		String[][] results = {expectedresult1, expectedresult2};
		
		for (int i = 0; i < 2; i ++) {
			//get either first/second test and expected result
			String[] test = tests[i];
			String[] expectedresult = results[i];
			
			//reverse array and check that each index matches expected result
			WarmUp.reverseArray(test);
			
			for (int j = 0; j < test.length; j++) {
				assertEquals(expectedresult[j], test[j]);
			}
		}
		
	}
	
	@Test
	void testCard() {
		Card card1 = new Card(1, "clubs");
		Card card2 = new Card(2, "hearts");
		Card card5 = new Card(5, "diamonds");
		Card card10 = new Card(10, "clubs");
		Card card11 = new Card(11, "spades");
		
		//check getFlippyCardValue
		
		//USE LINES 92-96 IF STUDENT USES ONLY POSITIVE FLIPPY CARD VALUES
		//COMMENT OUT LINES 98-109; OTHERWISE DO REVERSE
		assertEquals(card1.getFlippyCardValue(), 11);
		assertEquals(card2.getFlippyCardValue(), 2);
		assertEquals(card5.getFlippyCardValue(), 5);
		assertEquals(card10.getFlippyCardValue(), 10);
		assertEquals(card11.getFlippyCardValue(), 10);
		
//		// IF STUDENT USES NEGATIVE VALUE FOR BLACK CARDS METHOD
//		assertEquals(card1.getFlippyCardValue(), -11);
//		assertEquals(card2.getFlippyCardValue(), 2);
//		assertEquals(card5.getFlippyCardValue(), 5);
//		assertEquals(card10.getFlippyCardValue(), -10);
//		assertEquals(card11.getFlippyCardValue(), -10);
		
		//check isRedCard
		assertFalse(card1.isRedCard());
		assertTrue(card2.isRedCard());
		assertTrue(card5.isRedCard());
		assertFalse(card11.isRedCard());
	}
	
	@Test
	void testFlipCard() {
		FlippyCards f = new FlippyCards(4);
		
		boolean preFlip = f.getCard(2).isFaceUp();
		f.flipCard(2);
		boolean postFlip = f.getCard(2).isFaceUp();
		
		//assert that isFaceUp() value of card is different after flip
		assertNotEquals(preFlip, postFlip);
	}
	
	@Test
	void testCalculateOptimal() {
		FlippyCards f = new FlippyCards(5);
		
		//calculate actual optimal result using solution code 
		int expectedResult = 0;
		
		for( int i = 0; i < 5; i++ ){
			if( f.getCard(i).isRedCard() ){
				expectedResult += f.getCard(i).getFlippyCardValue();
			}
		}
		
		int actualResult = f.calculateOptimalScore();
		assertEquals(expectedResult, actualResult);	
	}
	
	@Test
	void testFaceUpTotal() {
		FlippyCards f = new FlippyCards(4);
		
		//calculate expected
		int expectedResult = 0;
		
		for( int i = 0; i < 4; i++ ){
			if( f.getCard(i).isFaceUp() ){
				if( f.getCard(i).isRedCard() ){
					expectedResult += f.getCard(i).getFlippyCardValue();
				}else{
					expectedResult -= f.getCard(i).getFlippyCardValue();
				}
			}
		}
		
		int actualResult = f.faceUpTotal();
		assertEquals(expectedResult, actualResult);
	}
	
	@Test
	void testFaceDownTotal() {
		FlippyCards f = new FlippyCards(5);
		
		//calculate expected
		int expectedResult = 0;
		
		//USE LINES 171-179 IF STUDENT USED ALL POSITIVE FLIPPY CARD VALUES
		//COMMENT OUT 181-185; DO REVERSE OTHERWISE
		for( int i = 0; i < 5; i++ ){
			if ( f.getCard(i).isFaceUp() == false ){
				if( f.getCard(i).isRedCard() ){
					expectedResult += f.getCard(i).getFlippyCardValue();
				}else{
					expectedResult -= f.getCard(i).getFlippyCardValue();
				}
			}
		}
		
//		for (int i = 0; i < 5; i++ ) {
//			if (f.getCard(i).isFaceUp() == false ) {
//				expectedResult += f.getCard(i).getFlippyCardValue();
//			}
//		}
		
		int actualResult = f.faceDownTotal();
		assertEquals(expectedResult, actualResult);
	}
	@Test
	void testToString() {
		/*
		 * note to future graders: because students differently handle the final space of the string,
		 * it's hard to check for string equality. instead this test just prints two strings to the console,
		 * one with all face down cards and one with the first and last cards flipped.
		 */
		FlippyCards f = new FlippyCards(5);
		
		//expected all face-down string
		String expected1 = "FACE-DOWN | FACE-DOWN | FACE-DOWN | FACE-DOWN | FACE-DOWN";
		String actual1 = f.toString();
		System.out.println(actual1);
		
		f.flipCard(0);
		f.flipCard(4);
		
		//expected string with first and last card face up
		String expected2 = "";
		
		for( int i = 0; i < 5; i++ ){
			if( f.getCard(i).isFaceUp() ){
				expected2 += f.getCard(i) + " | ";
			}else{
				expected2 += "FACE-DOWN | ";
			}
		}
		
		//checking proper handling of toString ending
		expected2 = expected2.substring(0, expected2.length()-2);
		
		String actual2 = f.toString();
		System.out.println(actual2);
	}
	
}

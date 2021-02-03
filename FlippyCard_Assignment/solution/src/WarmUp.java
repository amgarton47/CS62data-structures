import java.util.Arrays;


public class WarmUp {
	public static int countHearts(Card[] cards ){
		int count = 0;
		
		for( int i = 0; i < cards.length; i++ ){
			if(cards[i].getSuit().equals("hearts")){
				count++;
			}
		}
		
		return count;
	}
	
	public static double[] addArraysSameLength(double[] array1, double[] array2){
		double finalArray[] = new double[array1.length];
		
		for( int i = 0; i < array1.length; i++ ){
			finalArray[i] = array1[i] + array2[i];
		}
		
		return finalArray;
	}
	
	public static double[] addArrays(double[] array1, double[] array2 ){
		int longer;
		
		if( array1.length > array2.length ){
			longer = array1.length;
		}else{
			longer = array2.length;
		}
		
		double[] finalArray = new double[longer];
		
		for( int i = 0; i < longer; i++ ){
			if( i < array1.length ){
				finalArray[i] += array1[i];
			}
			
			if( i < array2.length ){
				finalArray[i] += array2[i];
			}
		}
		
		return finalArray;
	}
	
	public static void reverseArray(String[] words){
		for( int i = 0; i < words.length/2; i++ ){
			String temp = words[i];
			words[i] = words[words.length - i - 1];
			words[words.length-i-1] = temp;
		}
	}

	public static void main(String[] args){
		double[] array1 = {1, 2, 3, 4, 5};
		
		double[] answer = addArrays(array1, array1);
		
		String[] words = {"I", "love", "my", "CS", "classes", "!"};
		
		System.out.println("Before: " + Arrays.toString(words));
		reverseArray(words);
		System.out.println("After: " + Arrays.toString(words));
	}
}

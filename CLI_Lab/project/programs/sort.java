import java.util.Random;

class sort {
	public static void main(String[] args) {
		Random rand = new Random();
		int[] list = new int[100000];
		for (int i = 1; i < 99999; i++) {
			list[i] = rand.nextInt(100);
		}
		for (int i = 0; i < 99999; i++) {
			for (int u = 0; u < 99998-i; u++) {
				if (list[u] > list[u+1]) {
					int temp = list[u];
					list[u] = list[u+1];
					list[u+1] = temp;
				}
			}
		}
		//for (int i = 1; i < 100000; i++) {
		//	System.out.print(list[i-1] + " ");
		//}
	}
}

import java.util.Scanner;

public class Lottomatchugi {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int max = 45, min = 1, you = 99;
		int lotto = (int) (Math.random() * 45 + 1);
		System.out.println(lotto);
		while (you != lotto) {
			System.out.print(min + " ~ " + max + " 중에 맞춰보세요. : ");
			you = scanner.nextInt();
			if (you <= 0 || you > 45) {
				System.out.print("1~45의 정수 중에 고르셔야 합니다.");
			} else if (you < lotto) {
				min = you + 1;
			} else {
				max = you - 1;
			}
		}
		System.out.println("맞췄어 축하해 ");
		scanner.close();
	}
}

import java.util.Scanner;

//오늘의 Quiz : 사용자로부터 원하는 단수(2~9) 구구단을 출력하는 프로그램을 구현하시오.
//(단, 단수를 매개변수로 받아 해당 단수의 구구단을 출력하는 부분을 method로 처리한다. 사용자가 2~9 사이의 수가 아닌 수를 입력할 시 2~9사이의 수를 입력할 때까지 계속 입력 받는다.)

public class Methodgugudan {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int scan;
		System.out.print("구구단(2~9)을 입력하시오 :");
		scan = scanner.nextInt();
			while(scan<2 || scan>9) {
				System.out.print("2~9중에 입력하세요 :");
				scan = scanner.nextInt();
			}
		for(int i=1;i<=9;i++) {
			System.out.printf("%d*%d=%d  ",scan,i,scan*i);
		}
		scanner.close();
	}
}

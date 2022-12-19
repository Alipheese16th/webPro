package com.lec.quiz;
//두 수를 입력받아 두 수가 같은지 결과를 O 나 X 로 출력.  
//첫번째 수가 더 큰지 결과를 O 나 X 로 출력한다.
import java.util.Scanner;
public class Quiz3 {
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		System.out.print("\"첫번째수를 입력해주세요.\" : ");
		int su1 = scanner.nextInt();
		
		System.out.print("\"두번째수를 입력해주세요.\" : ");
		int su2 = scanner.nextInt();
		
		System.out.println("두 수가 같은가 : "+((su1==su2)?'O':'X'));
		System.out.println("첫번째 수가 더 큰가 : "+((su1>su2)?'O':'X'));
		
		scanner.close();
	}
}

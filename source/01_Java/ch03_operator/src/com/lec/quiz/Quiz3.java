package com.lec.quiz;

import java.util.Scanner;

//두 수를 입력받아 두 수가 같은지 결과를 O 나 X 로 출력.  
//첫번째 수가 더 큰지 결과를 O 나 X 로 출력한다.
public class Quiz3 {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("\"첫번째수를 입력해주세요.\" : ");
		int su1 = scanner.nextInt();
		System.out.print("\"두번째수를 입력해주세요.\" : ");
		int su2 = scanner.nextInt();
		char result1 = (su1 == su2) ? 'O' : 'X';
		System.out.printf("두 수가 같은가(%d == %d) : %c%n", su1, su2, result1);
		char result2 = (su1 > su2) ? 'O' : 'X';
		System.out.printf("첫번째수가 더 큰가(%d > %d) : %c%n", su1, su2, result2);
		scanner.close();
	}
}

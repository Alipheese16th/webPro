package com.lec.quiz;
//quiz2 입력한 수가 짝수인지 홀수인지 출력
import java.util.Scanner;
public class Quiz2 {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("\"홀수짝수판별기\" : ");
		int su = scanner.nextInt();
		int result = su%2;
		System.out.println((result==0)?" 짝수입니다.":" 홀수입니다.");
		scanner.close();

	}
}

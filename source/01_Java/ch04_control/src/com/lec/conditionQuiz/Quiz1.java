package com.lec.conditionQuiz;
import java.util.Scanner;
//수를 입력 받아 절대값을 출력하는 프로그램
public class Quiz1 {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("정수를 입력하십시오. : ");
		int num = scanner.nextInt();
		if(num<0) {
			System.out.println(-num);
		}else{
			System.out.println(num);
		}
		scanner.close();
	}
}

package com.lec.ex;

import java.util.Scanner;

// 사용자로부터 원하는 단수를 매개변수로 받아 구구단을 출력하는 메소드를 만들자. 
//2~9의 수가 아닐시 입력 받는 시도를 반복
public class methodQuiz {

	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		int num;
		
		do {
			System.out.print("구구단 시작! (2~9)중의 정수를 입력하세요. : ");
			num = scanner.nextInt();
		} while (!(2 <= num && num <= 9));
		
		gugu(num);
		scanner.close();
		
	}// main

	private static void gugu(int num) {
		
		for (int i = 1; i < 10; i++) {
			System.out.printf("%d*%d = %d%n", num, i, num * i);
		}
		
		System.out.println("정수 '" + num + "'의 구구단 완성!");
	}// gugu

}// class

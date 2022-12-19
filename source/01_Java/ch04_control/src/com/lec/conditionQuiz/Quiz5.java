package com.lec.conditionQuiz;
//현재 몇월인지를 키보드로부터 입력받아 계절을 출력하는 프로그램을 구현
import java.util.Scanner;
public class Quiz5 {
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		System.out.print("현재 날짜의 월月을 정수로 입력해주세요 : ");
		int mon = scanner.nextInt();
		
		switch (mon) {
			case 3: case 4: case 5: 
				System.out.println("봄 입니다.");
				break;
			case 6: case 7: case 8: 
				System.out.println("여름 입니다.");
				break;
			case 9: case 10: case 11: 
				System.out.println("가을 입니다.");
				break;
			case 12: case 1: case 2: 
				System.out.println("겨울 입니다.");
				break;
			default:
				System.out.println("잘못된 값을 입력하셨습니다.");
				break;
		}
		scanner.close();
		
	}
}



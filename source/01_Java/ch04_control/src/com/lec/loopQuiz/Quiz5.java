package com.lec.loopQuiz;

//컴퓨터와 계속 가위바위보를 할수있다. -1을 누르면 종료한다 
import java.util.Scanner;

public class Quiz5 {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int you = 0, com;
		

		while (you != -1) {
			System.out.print("가위(0), 바위(1), 보(2) 중 하나를 내십시오 : ");
			you = scanner.nextInt();
			com = (int) (Math.random() * 3);
			if (you >= 3 || you < 0) {
				System.out.println("잘못 입력하셨습니다. 0 ~ 2 중에 입력하세요");
			} else if ((you + 1) % 3 == com) {
				System.out.println("computer는 (" + com + ")를 냈습니다. computer의 승리입니다.");
			} else if ((you + 2) % 3 == com) {
				System.out.println("computer는 (" + com + ")를 냈습니다. 당신의 승리입니다.");
			} else {
				System.out.println("computer는 (" + com + ")를 냈습니다. 비겼습니다.");
			}
		}
		System.out.println("게임을 종료합니다.");
		scanner.close();
	}
}

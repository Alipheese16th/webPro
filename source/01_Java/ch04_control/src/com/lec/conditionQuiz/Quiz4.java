package com.lec.conditionQuiz;
import java.util.Scanner;
public class Quiz4 {
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		System.out.print("가위(0), 바위(1), 보(2) 중 하나를 내십시오 : ");
		int you = scanner.nextInt();
		
		int com = (int)(Math.random()*3);
		
		if(you>=3 || you<0) {
			System.out.println("잘못 입력하셨습니다. 0 ~ 2 중에 입력하세요");
			
		}else if((you+1)%3==com){
			System.out.println("computer는 ("+com+")를 냈습니다. computer의 승리입니다.");
			
		}else if((you+2)%3==com){
			System.out.println("computer는 ("+com+")를 냈습니다. 당신의 승리입니다.");
			
		}else {
			System.out.println("computer는 ("+com+")를 냈습니다. 비겼습니다.");
		}
		scanner.close();
		
	}
}

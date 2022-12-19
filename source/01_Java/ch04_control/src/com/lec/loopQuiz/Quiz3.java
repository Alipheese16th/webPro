package com.lec.loopQuiz;
//퀴즈3 : 사용자로부터 원하는 구구단 수를 입력받아 해당 구구단을 출력해 보자 
import java.util.Scanner;
public class Quiz3 {
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		System.out.print("구구단(1~9중에 입력하세요) : ");
		int su = scanner.nextInt();
		
		if(0<su && su<10) {
			
			for(int i=1;i<10;i++) {
				System.out.println(su+"*"+i+"="+(su*i));
			}
			
		}else {
			System.out.println("잘못된 입력값입니다. 1~9 중에 입력하세요");
		}
		
		scanner.close();
	}
}

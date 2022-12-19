package com.lec.loopQuiz;
//퀴즈4 : 다음과 같은 출력형식의 구구단을 출력하는 프로그램을 구현해보자
public class Quiz4 {
	public static void main(String[] args) {
		
		for(int i=1;i<10;i++) {
			
			 for(int j=2;j<10;j++) {
				 System.out.print(""+j+"*"+i+"="+(i*j)+"  ");
			 }
			 
			 System.out.println();
		}
		
	}
}

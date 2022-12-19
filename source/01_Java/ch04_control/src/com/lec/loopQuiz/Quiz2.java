package com.lec.loopQuiz;
//퀴즈2 : 1~10까지 숫자 중 짝수 또는 홀수의 합을 구해보자
public class Quiz2 {
	public static void main(String[] args) {
		
		int jjacksu = 0;
		int holsu = 0;
		
		for(int i=1;i<11;i++) {
			 if(i%2==0) {
				 jjacksu += i;
			 }else {
				 holsu += i;
			 }
		}
		
		System.out.println("1~10까지의 짝수의 합은 "+jjacksu);
		System.out.println("1~10까지의 홀수의 합은 "+holsu);
		
	}
}

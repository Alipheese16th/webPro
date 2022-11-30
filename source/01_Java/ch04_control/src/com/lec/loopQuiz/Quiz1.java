package com.lec.loopQuiz;
//퀴즈1 : 1부터 10까지의 곱의 결과는?
public class Quiz1 {
	public static void main(String[] args) {
		int total=1;
		for(int i=1;i<11;i++) {
			 total *= i;
		}
		System.out.println(total);
	}
}

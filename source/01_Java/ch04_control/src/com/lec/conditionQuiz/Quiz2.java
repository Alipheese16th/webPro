package com.lec.conditionQuiz;
public class Quiz2 {
	public static void main(String[] args) {
		
		int num1 = 27;
		int num2 = 32;
		int max; //변수를 여기서 선언해야 가능
		
		if(num1>num2) {
			max = num1;  //여기서 변수 선언 해봣자 아래에서 프린트식을 못쓴다 
		}else {
			max = num2;
		}
		
		System.out.println("최대값은 "+max);
		
		
	}
}

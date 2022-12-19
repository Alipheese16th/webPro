package com.lec.method;
//sum(10,100); sum(10); - 메소드의 오버로딩 (매개변수를 달리해서 여러기능을 쓸수있다) 자바의장점
//evenOdd(55)  / abs(-9)
public class Arithmetic {
	
	public int sum(int from,int to) { //from부터 to까지 정수 누적 값 return
		
		int result = 0;
		
		for(int i=from;i<=to;i++) {
			result+=i;
		}
		
		return result;
	}
	
	public int sum(int to) { // 1~to까지 정수 누적 값 return
		
		int result = 0;
		
		for(int i=1;i<=to;i++) {
			result+=i;
		}
		
		return result;
	}
	
	public String evenOdd(int value) {
			return value%2==0?"짝수입니다":"홀수입니다";
	}
	
	public static int abs(int value) { //접근제한자 생략하면 같은 패키지에서만 사용가능 
		return value<0?-value:value;
	}
	
	
}

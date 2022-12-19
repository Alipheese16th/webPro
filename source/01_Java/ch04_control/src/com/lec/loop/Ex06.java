package com.lec.loop;

//loop문을 작성할 때는 무한반복문에 들어가지 않도록 주의
public class Ex06 {
	public static void main(String[] args) {
		
		for (int i = 0;; i++) { // 조건이 true
			System.out.println(i + "안녕하세요");
		}
		// System.out.println("실행이 안됨");
	}
}

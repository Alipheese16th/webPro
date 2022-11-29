package com.lec.ex;

public class VarEx04 {

	public static void main(String[] args) {
		// 변수 선언은 위치 제약이 없다. 다만 위에 선언 하는게 좋다.
		char c = '씨'; 				// 2byte
		int i = 20; 				// 4byte
		long l = 2200000000l; 		// 8byte
		System.out.println("l = " + l);
		boolean b = true; 			// 1byte
		System.out.println("b = " + b);
		float f = 3.14159265359f; 	// 4byte
		double d = 3.14159265359; 	// 8byte
		System.out.println("f = " + f);
		System.out.println("d = " + d);
		if (f == d) {
			System.out.println("f와 d값은 같다");
		} else {
			System.out.println("f와 d값은 다르다");
		}
		System.out.println("==========================");
		f = 10.1f;
		d = 10.1;
		if (f == d) {
			System.out.println("f와 d값은 같다");
		} else {
			System.out.println("f와 d값은 다르다");
		}
		// 정수(4byte)랑 실수(8byte)랑 연산한 결과 : i+d(8byte)
		System.out.println("i + d = " + (i + d));
		// 정수(4byte)랑 정수(4byte)랑 연산한 결과 : 4byte
		System.out.println("i / 7 = " + (i / 7));
		// 반드시 정수타입으로 나눈다. 자바에서 / 란 나누기가 아닌 그저 몫을 구하는 연산자 일뿐이다.
		
	}
}

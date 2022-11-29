package com.lec.ex;

// println() : 콘솔창 출력 (개행포함)
// print() : 콘솔창 출력(개행 미포함)
// printf() : 콘솔창 출력 (포맷 지정)
public class VarEx02 {
	public static void main(String[] args) {
		// 타입 변수명 할당연산자 값;
		int i = 10;
		byte j = 127;
		double h = 10.15;
		i = 100; // 값을 계속 바꿔서 넣을수 있다.
		System.out.println("i=" + i + "\tj=" + j + "\th=" + h);
		System.out.printf("i=%d \t j=%d \t h=%.2f \n", i, j, h);
		char c1 = '김';
		char c2 = '홯';
		// %c:문자, %s:문자열, %d:정수, %f:실수, %b:boolean
		// System.out.printf(Format,args);
		System.out.printf("c1=%c \t c2=%c\n", c1, c2);
		System.out.printf("c1=%d \t c2=%d\n", (int) c1, (int) c2);
		System.out.println("프로그램끝 ");

	}
}
// 논리형 boolean 	1 byte true, false
// 문자형 char 	2 byte 0~65,535
// 정수형 byte 	1 byte -128 ~ 127 (-27~ 27-1)
// 		short 	2 byte -32,768 ~ 32,767 (-215~ 215-1)
// 		int 	4 byte -2,147,483,648 ~ 2,147,483,647
// 		long 	8 byte -9,223,372,036,854,775,808 ~ 9,223,372,036,854,775,807
// 실수형 float 	4 byte -3.4E38 ~ +3.4E38
// 		double 	8 byte 1.7E308 ~ + 1.7E308

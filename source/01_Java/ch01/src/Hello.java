/*
여러줄 주석 (컴파일 제외)
 */

// 한줄 주석

public class Hello {
	//자바 파일명은 클래스 명과 동일. 대문자로 시작. 
	//JVM 자바가상머신이 실행하는  main함수
	public static void main(String[] args) {

		System.out.println("Hello , World");

		int age = 10;
		String adr = "Seoul";
		float f = 1.23456789f;
		double d = 1.23456789;

		System.out.printf("Im in %s and age is %d%n",adr,age);
		System.out.printf("%f%n",f);
		System.out.printf("%.3f%n",f);
		System.out.printf("%10.8f%n",d);
		System.out.printf("%e%n",d);
		System.out.printf("%o%n",age);
		System.out.printf("%x%n",age);
		

	}
}

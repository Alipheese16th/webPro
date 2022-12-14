package com.lec.ex1_trycatch;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Ex02 {
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		int i, j =1; //사용자에게 입력받을 수를 저장
		System.out.print("첫번째 정수는?");
		
		do {
			try {
				i = scanner.nextInt();
				break;
				
			}catch(InputMismatchException e) {
				System.out.println("수에 문자를 넣지마세요");
				scanner.nextLine();					//버퍼에 /n이 남아있으면 다음 try문에서 그냥 넘어간다
			}
		}while(true);
		
		System.out.print("두번째 정수는?");
		
		try {
			j = scanner.nextInt();
			System.out.println("i / j = " + (i/j));	//예외가 발생할 수도 있는 부분
		}catch(ArithmeticException e) {
			System.out.println(e.getMessage());
		}catch(InputMismatchException e) {
			System.out.println("예외 메세지 : "+e.getMessage());
			System.out.println("j를 잘못 입력하셔서 1로 처리합니다");
		}
		
		System.out.println("i = "+i+", j = "+j);
		System.out.println("i * j = " + (i*j));
		System.out.println("i + j = " + (i+j));
		System.out.println("i - j = " + (i-j));
		System.out.println("Done");
		
		scanner.close();
		
		
	}

}

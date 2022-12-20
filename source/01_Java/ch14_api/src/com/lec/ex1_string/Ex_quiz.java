package com.lec.ex1_string;

import java.util.Scanner;

//예제. 반복적으로 전화번호를 입력받아 다음과 같은 형식을 출력하는 반복문으로 구현하시오. 
//전화번호 대신 x를 입력하면 종료합니다
public class Ex_quiz {

	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		String tel;
		
		while(true){
			System.out.println("전화번호를 입력하세요(x를 입력하면 종료) :");
			tel = scanner.next();
			
			if(tel.equalsIgnoreCase("x"))
				break;
			
			System.out.println("입력한 전화번호 : "+tel);
			
			System.out.println("전화번호 앞자리는"+tel.substring(0,tel.indexOf("-")));
			System.out.println("전화번호 뒷자리는"+tel.substring(tel.lastIndexOf("-")+1));
			
		}
		
		scanner.close();
	}
}

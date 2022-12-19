package com.lec.ex1_string;

import java.util.Scanner;
//	전화번호 입력받아 요리조리 별거 다 출력.
public class Ex06_telprint {
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		while(true) {
			System.out.print("전화번호를 입력하세요(xxx-xxx-xxxx)-종료를 원하면(x) :");
			String tel = scanner.next();
			
			if(tel.equalsIgnoreCase("x")) break;
			
			System.out.println("입력한 전화번호 : "+tel);
			
			System.out.print("짝수번째 문자열 : ");
			for (int i = 0; i < tel.length(); i++) {
				if(i%2==0)
					System.out.print(tel.charAt(i));
				else 
					System.out.print(" ");
			}
			System.out.println();
			
			System.out.print("문자를 거꾸로 : ");
			
			for (int i = tel.length()-1; i >= 0; i--) {
				System.out.print(tel.charAt(i));
			}System.out.println();
			
			System.out.println("전화번호 앞자리는 : "+tel.substring(0,tel.indexOf("-")));
			System.out.println("전화번호 뒷자리는 : "+tel.substring(tel.lastIndexOf("-")+1));
			
		}
		
		scanner.close();
	}//main
}

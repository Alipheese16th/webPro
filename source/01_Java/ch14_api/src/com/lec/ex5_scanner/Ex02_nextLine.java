package com.lec.ex5_scanner;

import java.util.Scanner;

public class Ex02_nextLine {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("주소는 : ");
		String address = scanner.nextLine();
		System.out.println("입력하신 주소는 "+address);
		
		System.out.print("나이 : ");
		int age = scanner.nextInt();
		System.out.println("나이는 "+age);
		
		System.out.print("이름 : ");
		scanner.nextLine();
		String name = scanner.nextLine();
		System.out.println("이름은 "+name);
		
		System.out.println("프로그램 끝");
		scanner.close();
	}

}

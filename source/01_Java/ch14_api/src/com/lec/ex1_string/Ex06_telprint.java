package com.lec.ex1_string;

import java.util.Scanner;
//	��ȭ��ȣ �Է¹޾� �丮���� ���� �� ���.
public class Ex06_telprint {
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		while(true) {
			System.out.print("��ȭ��ȣ�� �Է��ϼ���(xxx-xxx-xxxx)-���Ḧ ���ϸ�(x) :");
			String tel = scanner.next();
			
			if(tel.equalsIgnoreCase("x")) break;
			
			System.out.println("�Է��� ��ȭ��ȣ : "+tel);
			
			System.out.print("¦����° ���ڿ� : ");
			for (int i = 0; i < tel.length(); i++) {
				if(i%2==0)
					System.out.print(tel.charAt(i));
				else 
					System.out.print(" ");
			}
			System.out.println();
			
			System.out.print("���ڸ� �Ųٷ� : ");
			
			for (int i = tel.length()-1; i >= 0; i--) {
				System.out.print(tel.charAt(i));
			}System.out.println();
			
			System.out.println("��ȭ��ȣ ���ڸ��� : "+tel.substring(0,tel.indexOf("-")));
			System.out.println("��ȭ��ȣ ���ڸ��� : "+tel.substring(tel.lastIndexOf("-")+1));
			
		}
		
		scanner.close();
	}//main
}

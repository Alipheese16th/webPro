package com.lec.ex;

import java.util.Scanner;

// ����ڷκ��� ���ϴ� �ܼ��� �Ű������� �޾� �������� ����ϴ� �޼ҵ带 ������. 
//2~9�� ���� �ƴҽ� �Է� �޴� �õ��� �ݺ�
public class methodQuiz {

	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		int num;
		
		do {
			System.out.print("������ ����! (2~9)���� ������ �Է��ϼ���. : ");
			num = scanner.nextInt();
		} while (!(2 <= num && num <= 9));
		
		gugu(num);
		scanner.close();
		
	}// main

	private static void gugu(int num) {
		
		for (int i = 1; i < 10; i++) {
			System.out.printf("%d*%d = %d%n", num, i, num * i);
		}
		
		System.out.println("���� '" + num + "'�� ������ �ϼ�!");
	}// gugu

}// class

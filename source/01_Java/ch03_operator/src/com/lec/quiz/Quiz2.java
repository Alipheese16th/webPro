package com.lec.quiz;

import java.util.Scanner;

//quiz2 �Է��� ���� ¦������ Ȧ������ ���
public class Quiz2 {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("\"Ȧ��¦���Ǻ���\" : ");
		int su = scanner.nextInt();
		String result = (su % 2 == 0) ? "¦���Դϴ�" : "Ȧ���Դϴ�";
		System.out.println("�Է��Ͻ� ��(" + su + ")" + result);
		scanner.close();

	}
}

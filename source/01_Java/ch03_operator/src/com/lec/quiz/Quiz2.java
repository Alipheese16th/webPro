package com.lec.quiz;
//quiz2 �Է��� ���� ¦������ Ȧ������ ���
import java.util.Scanner;
public class Quiz2 {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("\"Ȧ��¦���Ǻ���\" : ");
		int su = scanner.nextInt();
		int result = su%2;
		System.out.println((result==0)?" ¦���Դϴ�.":" Ȧ���Դϴ�.");
		scanner.close();

	}
}

package com.lec.quiz;

import java.util.Scanner;

//�� ���� �Է¹޾� �� ���� ������ ����� O �� X �� ���.  
//ù��° ���� �� ū�� ����� O �� X �� ����Ѵ�.
public class Quiz3 {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("\"ù��°���� �Է����ּ���.\" : ");
		int su1 = scanner.nextInt();
		System.out.print("\"�ι�°���� �Է����ּ���.\" : ");
		int su2 = scanner.nextInt();
		char result1 = (su1 == su2) ? 'O' : 'X';
		System.out.printf("�� ���� ������(%d == %d) : %c%n", su1, su2, result1);
		char result2 = (su1 > su2) ? 'O' : 'X';
		System.out.printf("ù��°���� �� ū��(%d > %d) : %c%n", su1, su2, result2);
		scanner.close();
	}
}

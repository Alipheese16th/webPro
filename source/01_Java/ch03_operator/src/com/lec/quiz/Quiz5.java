package com.lec.quiz;

import java.util.Scanner;

//����, ����, ���� ������ ����ڿ��� �Է¹޾�, �� ������ ����ϰ� ����, ���(�Ҽ���2�ڸ�)�� ����ϴ� ���α׷� ����
public class Quiz5 {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		System.out.print("\"���������� �Է��ϼ���.\" : ");
		int su1 = scanner.nextInt();
		System.out.print("\"���������� �Է��ϼ���.\" : ");
		int su2 = scanner.nextInt();
		System.out.print("\"���������� �Է��ϼ���.\" : ");
		int su3 = scanner.nextInt();
		int total = su1 + su2 + su3;
		double avg = (su1 + su2 + su3) / 3.;
		System.out.printf("���� ���� : %d , ���� ���� : %d , ���� ���� : %d%n", su1, su2, su3);
		System.out.printf("���� ���� : %d%n", total);
		System.out.printf("��� ���� : %.2f", avg);
		scanner.close();
	}
}

package com.lec.quiz;

import java.util.Scanner;

//���̸� �Է¹޾� �Է¹��� ���̰� 65�� �̻��� ��, "��ο��"���, �ƴϸ� "�Ϲ�"���
public class Quiz4 {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		System.out.print("\"���̸� �Է��ϼ���.\" : ");
		int age = scanner.nextInt();
		String result = (age >= 65) ? "��ο��" : "�Ϲ�";
		System.out.printf("���̰� %d�� �Դϴ�. %s�Դϴ�.", age, result);
		scanner.close();
	}
}

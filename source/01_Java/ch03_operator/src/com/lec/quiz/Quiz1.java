package com.lec.quiz;

import java.util.Scanner;

//3�� ��� ���� �Ǵ��ϱ�: ����ڷκ��� ���ڸ� �Է� �ް� , �Է� ���� ���ڰ� 3�� ������� �ľ��Ѵ�.
public class Quiz1 {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("������ �Է��ϼ��� : ");
		int su = scanner.nextInt(); // Ű����κ��� �Է¹��� ������ ��ȯ�ϴ� ���
		String result = (su % 3 == 0) ? "3�� ����Դϴ�" : "3�� ����� �ƴմϴ�";
		System.out.println("�Է��Ͻ� ��(" + su + ")" + result);
		scanner.close();

	}
}

package com.lec.quiz;
//���̸� �Է¹޾� �Է¹��� ���̰� 65�� �̻��� ��, "��ο��"���, �ƴϸ� "�Ϲ�"���
import java.util.Scanner;
public class Quiz4 {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("\"���̸� �Է��ϼ���.\" : ");
		int age = scanner.nextInt();
		String result = (age>=65)?"��ο���Դϴ�.":
			((age>=0)?"�Ϲ��Դϴ�.":"�����Դϴ�.");
		System.out.printf("���̰� %d�� �Դϴ�. %s%n", age, result);
		scanner.close();
	}
}

package com.lec.quiz;
//3�� ��� ���� �Ǵ��ϱ�: ����ڷκ��� ���ڸ� �Է� �ް� , �Է� ���� ���ڰ� 3�� ������� �ľ��Ѵ�.
import java.util.Scanner;
public class Quiz1 {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("������ �Է��ϼ��� : ");
		int su = scanner.nextInt(); // Ű����κ��� �Է¹��� ������ ��ȯ�ϴ� ���
		String result = (su % 3 == 0) ? "3�� ����Դϴ�" : "3�� ����� �ƴմϴ�";
		System.out.printf("�Է��Ͻ� ��(%d)�� %s%n",su,result);
		scanner.close();

	}
}

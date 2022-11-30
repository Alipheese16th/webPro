package com.lec.loop;

import java.util.Scanner;

public class Ex11doWhile_lotto2 {
	public static void main(String[] args) {

		int lotto, su;
		int min = 1, max = 45;
		Scanner scanner = new Scanner(System.in);
		lotto = (int) (Math.random() * 45 + 1);
		do {
			System.out.printf("�ζ� ��ȣ �Ѱ��� ���� ������(%d~%d) : ", min, max);
			su = scanner.nextInt();
			if (su < min || su > max) {
				System.out.println("�־��� ������ �����ϼ���");
			} else if (su > lotto) {
				max = su - 1;
			} else if (su < lotto) {
				min = su + 1;
			}
		} while (su != lotto);
		System.out.println("�������� ���߼̽��ϴ�.");
		scanner.close();
	}

}

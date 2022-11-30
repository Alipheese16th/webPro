package com.lec.loop;

import java.util.Scanner;

public class Ex11doWhile_lotto2 {
	public static void main(String[] args) {

		int lotto, su;
		int min = 1, max = 45;
		Scanner scanner = new Scanner(System.in);
		lotto = (int) (Math.random() * 45 + 1);
		do {
			System.out.printf("로또 번호 한개를 맞춰 보세요(%d~%d) : ", min, max);
			su = scanner.nextInt();
			if (su < min || su > max) {
				System.out.println("주어진 범위를 참조하세요");
			} else if (su > lotto) {
				max = su - 1;
			} else if (su < lotto) {
				min = su + 1;
			}
		} while (su != lotto);
		System.out.println("축하축하 맞추셨습니다.");
		scanner.close();
	}

}

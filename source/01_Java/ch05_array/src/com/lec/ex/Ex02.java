package com.lec.ex;

public class Ex02 {

	public static void main(String[] args) {
		int i = 10;
		int j = i;
		j = 99;
		System.out.printf("i = %d , j =%d\n", i, j);
		// j�� ���� �ٲ���� i�� �ٲ۰� �ƴϴ�
		// j�ȿ� �޸� �ȿ� ���� �ٲ��

		int[] score = { 10, 20, 30, 40, 50 };
		int[] s = score;
		s[0] = 99;
		for (int idx = 0; idx < s.length; idx++) {
			System.out.printf("score[%d] = %d\t s[%d] = %d\n", idx, score[idx], idx, s[idx]);
		}
		// score�� s�� �ּҰ� ����ִ°��̱⶧���� �迭�ǰ��� �ٲٸ� �� �ּҸ� ���� �迭���� �� ������ �ȴ�.
		// s�ȿ��� ���� ���� �ּҸ� �ִ�. ������ ����. ������(�ּҰ�)

	}
}

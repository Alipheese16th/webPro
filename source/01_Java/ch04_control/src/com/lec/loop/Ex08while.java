package com.lec.loop;

/* i�� 1�϶� �������� 1�̴�
   i�� 10�϶� �������� 55�̴� */
public class Ex08while {
	public static void main(String[] args) {
		int tot = 0; // ������ ����
		for (int i = 1; i < 11; i++) {
			tot += i;
			System.out.printf("i�� %d�� �� �������� %d�̴�%n", i, tot);
		}

		int tot2 = 0;
		int j = 1;
		while (j < 11) {
			tot2 += j;
			System.out.printf("i�� %d�� �� �������� %d�̴�%n", j, tot2);
			j++;
		}

	}
}

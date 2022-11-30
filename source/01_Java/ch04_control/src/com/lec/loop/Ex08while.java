package com.lec.loop;

/* i가 1일때 누적합은 1이다
   i가 10일때 누적합은 55이다 */
public class Ex08while {
	public static void main(String[] args) {
		int tot = 0; // 누적합 변수
		for (int i = 1; i < 11; i++) {
			tot += i;
			System.out.printf("i가 %d일 때 누적합은 %d이다%n", i, tot);
		}

		int tot2 = 0;
		int j = 1;
		while (j < 11) {
			tot2 += j;
			System.out.printf("i가 %d일 때 누적합은 %d이다%n", j, tot2);
			j++;
		}

	}
}

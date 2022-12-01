package com.lec.ex;

// 0 <= Math.random() < 1  - 0 ~ 0.9 실수
// 0 <= Math.random()*45 < 45 - 0 ~ 44 실수
// 1 <= Math.random()*45+1 < 46 - 1 ~ 45 실수
// 1 <= (int)(Math.random()*45+1) < 46 - 1~45 정수
public class Ex06_lotto {
	public static void main(String[] args) {
		int[] lotto = new int[6];
		int i, j, temp;
		for (i = 0; i < lotto.length; i++) {
			do { // 발생된 난수가 중복되었는지 체크
				temp = (int) (Math.random() * 45 + 1);
				for (j = 0; j < i; j++) {
					if (lotto[j] == temp) {
						System.out.println("중복되서 다시 실행" + temp);
						break;
					} // temp랑 같은 번호가 있으면 for문을 빠져나감.
				}
			} while (i != j);
			lotto[i] = temp;
		}
		// 로또 번호 출력
		for (int l : lotto) {
			System.out.print(l + "\t");
		}

		// lotto 배열 값을 sort 작은수 부터 정렬 한다.
		// i가 0일때 j와 비교해서 i가 작은쪽으로 바꾼다
		// i가 1일때 j랑 비교
		// 배열이0~5일때 i는0~4 j는 1~5
		System.out.println("\n정렬후");
		for (i = 0; i < lotto.length - 1; i++) {
			for (j = i + 1; j < lotto.length; j++) {
				if (lotto[i] > lotto[j]) {
					temp = lotto[i];
					lotto[i] = lotto[j];
					lotto[j] = temp;
				}
			}
		}
		for (int l : lotto) {
			System.out.print(l + "\t");
		}

	}
}

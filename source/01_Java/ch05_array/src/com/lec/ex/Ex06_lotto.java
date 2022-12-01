package com.lec.ex;

// 0 <= Math.random() < 1  - 0 ~ 0.9 �Ǽ�
// 0 <= Math.random()*45 < 45 - 0 ~ 44 �Ǽ�
// 1 <= Math.random()*45+1 < 46 - 1 ~ 45 �Ǽ�
// 1 <= (int)(Math.random()*45+1) < 46 - 1~45 ����
public class Ex06_lotto {
	public static void main(String[] args) {
		int[] lotto = new int[6];
		int i, j, temp;
		for (i = 0; i < lotto.length; i++) {
			do { // �߻��� ������ �ߺ��Ǿ����� üũ
				temp = (int) (Math.random() * 45 + 1);
				for (j = 0; j < i; j++) {
					if (lotto[j] == temp) {
						System.out.println("�ߺ��Ǽ� �ٽ� ����" + temp);
						break;
					} // temp�� ���� ��ȣ�� ������ for���� ��������.
				}
			} while (i != j);
			lotto[i] = temp;
		}
		// �ζ� ��ȣ ���
		for (int l : lotto) {
			System.out.print(l + "\t");
		}

		// lotto �迭 ���� sort ������ ���� ���� �Ѵ�.
		// i�� 0�϶� j�� ���ؼ� i�� ���������� �ٲ۴�
		// i�� 1�϶� j�� ��
		// �迭��0~5�϶� i��0~4 j�� 1~5
		System.out.println("\n������");
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

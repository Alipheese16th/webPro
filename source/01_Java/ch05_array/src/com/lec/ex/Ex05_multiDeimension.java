package com.lec.ex;

//�迭�� ��κ� 1���� �迭�� ���� Ư���� �������� ������ �迭�� ����. (������ ������ �ӽŷ��� ��)
public class Ex05_multiDeimension {

	public static void main(String[] args) {
		int[] arr = { 1, 2, 3 }; // 1���� �迭
		System.out.println(arr.length);

		int[][] test = { { 1, 2, 3, 4 }, { 5, 6, 7 } }; // 2���� �迭. �迭�� �迭�� �����ٴ� ����
		System.out.println(test.length);
		System.out.println(test[0].length);
		System.out.println(test[1][2]);
		test[0][1] = 200;
		// ������ �迭�� ���
		for (int i = 0; i < test.length; i++) {
			for (int j = 0; j < test[i].length; j++) {
				System.out.printf("test[%d][%d]=%d\n", i, j, test[i][j]);
			} // for-j
		} // for-i

	}// main
}// class

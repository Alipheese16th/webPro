package com.lec.ex;

//배열은 대부분 1차원 배열을 쓰나 특정한 군에서는 다차원 배열을 쓴다. (빅데이터 딥러닝 머신러닝 등)
public class Ex05_multiDeimension {

	public static void main(String[] args) {
		int[] arr = { 1, 2, 3 }; // 1차원 배열
		System.out.println(arr.length);

		int[][] test = { { 1, 2, 3, 4 }, { 5, 6, 7 } }; // 2차원 배열. 배열을 배열로 묶었다는 느낌
		System.out.println(test.length);
		System.out.println(test[0].length);
		System.out.println(test[1][2]);
		test[0][1] = 200;
		// 다차원 배열의 출력
		for (int i = 0; i < test.length; i++) {
			for (int j = 0; j < test[i].length; j++) {
				System.out.printf("test[%d][%d]=%d\n", i, j, test[i][j]);
			} // for-j
		} // for-i

	}// main
}// class

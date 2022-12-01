package com.lec.test;

//76,45,34,89,100,50,90,93 8개의 값을 1차원 배열로 초기화 하고 
// 값에 합계와 평균 그리고 최대값과 최소값을 구하는 프로그램을 작성
public class ProgramingLan {
	public static void main(String[] args) {
		int[] array = { 76, 45, 34, 89, 100, 50, 90, 93 };
		int i, tot = 0;
		for (i = 0; i < array.length; i++) {
			tot += array[i];
		}
		double avg = (double) tot / array.length; // 합계와 평균을 구함

		int max = array[0], min = array[0]; // 최대값과 최소값을 구함
		for (i = 1; i < array.length; i++) {
			if (array[i] > max) {
				max = array[i];
			}
			if (array[i] < min) {
				min = array[i];
			}
		}
		System.out.printf("값의 합은 %d이고 평균은 %.2f이다.\n" + "그리고 최대값은 %d, 최소값은 %d이다.", tot, avg, max, min);

	}
}

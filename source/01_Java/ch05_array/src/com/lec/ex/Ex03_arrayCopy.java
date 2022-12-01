package com.lec.ex;

public class Ex03_arrayCopy {

	public static void main(String[] args) {
		int[] score = { 10, 20, 30, 40, 50 };
		int[] s = new int[score.length];
//		for(int idx=0;idx<s.length;idx++) {
//			s[idx]=score[idx];
//		}
		System.arraycopy(score, 0, s, 0, score.length); // 배열 복제를 이렇게 간단하게 쓸수있는 메소드

		s[0] = 999;
		for (int idx = 0; idx < s.length; idx++) {
			System.out.printf("score[%d]=%d\ts[%d]=%d\n", idx, score[idx], idx, s[idx]);
		}

	}
}

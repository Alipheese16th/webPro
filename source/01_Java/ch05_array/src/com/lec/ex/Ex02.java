package com.lec.ex;

public class Ex02 {

	public static void main(String[] args) {
		int i = 10;
		int j = i;
		j = 99;
		System.out.printf("i = %d , j =%d\n", i, j);
		// j의 값이 바뀐거지 i를 바꾼게 아니다
		// j안에 메모리 안에 값이 바뀐다

		int[] score = { 10, 20, 30, 40, 50 };
		int[] s = score;
		s[0] = 99;
		for (int idx = 0; idx < s.length; idx++) {
			System.out.printf("score[%d] = %d\t s[%d] = %d\n", idx, score[idx], idx, s[idx]);
		}
		// score와 s는 주소가 들어있는것이기때문에 배열의값을 바꾸면 그 주소를 쓰는 배열들은 다 적용이 된다.
		// s안에는 값이 없고 주소만 있다. 참조형 변수. 참조값(주소값)

	}
}

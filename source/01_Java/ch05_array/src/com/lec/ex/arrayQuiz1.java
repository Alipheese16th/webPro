package com.lec.ex;

//�迭�� ��� ���� ���ϴ� ���α׷��� �ۼ�
public class arrayQuiz1 {

	public static void main(String[] args) {
		int[] arr = { 10, 20, 30, 40, 50 };
		int tot = 0;
		for (int i = 0; i < arr.length; i++) {
			tot += arr[i];
		}
		System.out.println(tot);
	}
}

package com.lec.ex;

//�Ž����� 2680�� 500,100,50,10¥�� �������� �ٶ� ��� �־�� �ϳ�?
public class arrayQuiz3 {

	public static void main(String[] args) {
		int[] coinUnit = { 500, 100, 50, 10 };
		int money = 2680;

		System.out.println("��� " + money + "����");
		for (int i = 0; i < coinUnit.length; i++) {
			if (money > coinUnit[i]) {
				System.out.println(coinUnit[i] + "��¥�� " + money / coinUnit[i] + "��");
				money %= coinUnit[i];
			}
		}

	}
}

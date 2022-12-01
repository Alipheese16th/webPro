package com.lec.ex;

//거스름돈 2680을 500,100,50,10짜리 동전으로 줄때 몇개씩 주어야 하나?
public class arrayQuiz3 {

	public static void main(String[] args) {
		int[] coinUnit = { 500, 100, 50, 10 };
		int money = 2680;

		System.out.println("결과 " + money + "원은");
		for (int i = 0; i < coinUnit.length; i++) {
			if (money > coinUnit[i]) {
				System.out.println(coinUnit[i] + "원짜리 " + money / coinUnit[i] + "개");
				money %= coinUnit[i];
			}
		}

	}
}

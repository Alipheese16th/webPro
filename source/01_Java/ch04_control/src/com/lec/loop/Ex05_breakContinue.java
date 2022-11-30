package com.lec.loop;

public class Ex05_breakContinue {
	public static void main(String[] args) {
		for (int i = 1; i <= 5; i++) {
			if (i == 3)
				// break; //반복문을 빠져나가라는 명령
				continue; // 증감식으로 올라간다.for문은 유지되나 특정사항에서 스킵이 가능한 명령
			System.out.println(i);
		}
	}
}

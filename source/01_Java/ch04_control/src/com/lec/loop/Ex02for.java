package com.lec.loop;

//결과: 1~20까지 누적합은 210      반복문을 활용한 누적분
// 두번째 결과 : 1+2+3+4+5+6+7+8+9+10+11+12+13+14+15+16+17+18+19+20=210
public class Ex02for {
	public static void main(String[] args) {
		int tot = 0; // 누적합을 위한 변수
		for (int i = 1; i <= 20; i++) {
			System.out.print(i);
			if (i != 20) {
				System.out.print('+');
			}
			tot = tot + i;
		}
		System.out.println("=" + tot);

	}
}

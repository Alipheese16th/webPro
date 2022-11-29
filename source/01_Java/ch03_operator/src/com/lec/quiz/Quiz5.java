package com.lec.quiz;

import java.util.Scanner;

//국어, 영어, 수학 점수를 사용자에게 입력받아, 각 점수를 출력하고 총점, 평균(소수점2자리)을 출력하는 프로그램 구현
public class Quiz5 {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		System.out.print("\"국어점수를 입력하세요.\" : ");
		int su1 = scanner.nextInt();
		System.out.print("\"영어점수를 입력하세요.\" : ");
		int su2 = scanner.nextInt();
		System.out.print("\"수학점수를 입력하세요.\" : ");
		int su3 = scanner.nextInt();
		int total = su1 + su2 + su3;
		double avg = (su1 + su2 + su3) / 3.;
		System.out.printf("국어 점수 : %d , 영어 점수 : %d , 수학 점수 : %d%n", su1, su2, su3);
		System.out.printf("총합 점수 : %d%n", total);
		System.out.printf("평균 점수 : %.2f", avg);
		scanner.close();
	}
}

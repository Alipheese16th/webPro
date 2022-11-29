package com.lec.quiz;
//국어, 영어, 수학 점수를 사용자에게 입력받아, 각 점수를 출력하고 총점, 평균(소수점2자리)을 출력하는 프로그램 구현
import java.util.Scanner;
public class Quiz5 {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("\"국어점수를 입력하세요.\" : ");
		int kor = scanner.nextInt();
		System.out.print("\"영어점수를 입력하세요.\" : ");
		int eng = scanner.nextInt();
		System.out.print("\"수학점수를 입력하세요.\" : ");
		int mat = scanner.nextInt();
		int total = kor+eng+mat;
		double avg = (kor+eng+mat)/3.;
		System.out.printf("국어 점수 : %d , 영어 점수 : %d , 수학 점수 : %d%n",kor,eng,mat);
		System.out.printf("총합 점수 : %d , 평균 점수 : %.2f",total,avg);
		scanner.close();
	}
}

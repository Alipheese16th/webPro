package com.lec.quiz;
//����, ����, ���� ������ ����ڿ��� �Է¹޾�, �� ������ ����ϰ� ����, ���(�Ҽ���2�ڸ�)�� ����ϴ� ���α׷� ����
import java.util.Scanner;
public class Quiz5 {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("\"���������� �Է��ϼ���.\" : ");
		int kor = scanner.nextInt();
		System.out.print("\"���������� �Է��ϼ���.\" : ");
		int eng = scanner.nextInt();
		System.out.print("\"���������� �Է��ϼ���.\" : ");
		int mat = scanner.nextInt();
		int total = kor+eng+mat;
		double avg = (kor+eng+mat)/3.;
		System.out.printf("���� ���� : %d , ���� ���� : %d , ���� ���� : %d%n",kor,eng,mat);
		System.out.printf("���� ���� : %d , ��� ���� : %.2f",total,avg);
		scanner.close();
	}
}

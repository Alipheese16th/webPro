package com.lec.quiz;
//�� ���� �Է¹޾� �� ���� ������ ����� O �� X �� ���.  
//ù��° ���� �� ū�� ����� O �� X �� ����Ѵ�.
import java.util.Scanner;
public class Quiz3 {
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		System.out.print("\"ù��°���� �Է����ּ���.\" : ");
		int su1 = scanner.nextInt();
		
		System.out.print("\"�ι�°���� �Է����ּ���.\" : ");
		int su2 = scanner.nextInt();
		
		System.out.println("�� ���� ������ : "+((su1==su2)?'O':'X'));
		System.out.println("ù��° ���� �� ū�� : "+((su1>su2)?'O':'X'));
		
		scanner.close();
	}
}

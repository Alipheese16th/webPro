package com.lec.conditionQuiz;
import java.util.Scanner;
//���� �Է� �޾� ���밪�� ����ϴ� ���α׷�
public class Quiz1 {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("������ �Է��Ͻʽÿ�. : ");
		int num = scanner.nextInt();
		if(num<0) {
			System.out.println(-num);
		}else{
			System.out.println(num);
		}
		scanner.close();
	}
}

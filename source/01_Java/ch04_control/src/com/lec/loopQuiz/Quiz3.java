package com.lec.loopQuiz;
//����3 : ����ڷκ��� ���ϴ� ������ ���� �Է¹޾� �ش� �������� ����� ���� 
import java.util.Scanner;
public class Quiz3 {
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		System.out.print("������(1~9�߿� �Է��ϼ���) : ");
		int su = scanner.nextInt();
		
		if(0<su && su<10) {
			
			for(int i=1;i<10;i++) {
				System.out.println(su+"*"+i+"="+(su*i));
			}
			
		}else {
			System.out.println("�߸��� �Է°��Դϴ�. 1~9 �߿� �Է��ϼ���");
		}
		
		scanner.close();
	}
}

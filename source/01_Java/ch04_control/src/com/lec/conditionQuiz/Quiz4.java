package com.lec.conditionQuiz;
import java.util.Scanner;
public class Quiz4 {
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		System.out.print("����(0), ����(1), ��(2) �� �ϳ��� ���ʽÿ� : ");
		int you = scanner.nextInt();
		
		int com = (int)(Math.random()*3);
		
		if(you>=3 || you<0) {
			System.out.println("�߸� �Է��ϼ̽��ϴ�. 0 ~ 2 �߿� �Է��ϼ���");
			
		}else if((you+1)%3==com){
			System.out.println("computer�� ("+com+")�� �½��ϴ�. computer�� �¸��Դϴ�.");
			
		}else if((you+2)%3==com){
			System.out.println("computer�� ("+com+")�� �½��ϴ�. ����� �¸��Դϴ�.");
			
		}else {
			System.out.println("computer�� ("+com+")�� �½��ϴ�. �����ϴ�.");
		}
		scanner.close();
		
	}
}

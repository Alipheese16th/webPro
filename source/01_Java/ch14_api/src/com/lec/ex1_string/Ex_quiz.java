package com.lec.ex1_string;

import java.util.Scanner;

//����. �ݺ������� ��ȭ��ȣ�� �Է¹޾� ������ ���� ������ ����ϴ� �ݺ������� �����Ͻÿ�. 
//��ȭ��ȣ ��� x�� �Է��ϸ� �����մϴ�
public class Ex_quiz {

	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		String tel;
		
		while(true){
			System.out.println("��ȭ��ȣ�� �Է��ϼ���(x�� �Է��ϸ� ����) :");
			tel = scanner.next();
			
			if(tel.equalsIgnoreCase("x"))
				break;
			
			System.out.println("�Է��� ��ȭ��ȣ : "+tel);
			
			System.out.println("��ȭ��ȣ ���ڸ���"+tel.substring(0,tel.indexOf("-")));
			System.out.println("��ȭ��ȣ ���ڸ���"+tel.substring(tel.lastIndexOf("-")+1));
			
		}
		
		scanner.close();
	}
}

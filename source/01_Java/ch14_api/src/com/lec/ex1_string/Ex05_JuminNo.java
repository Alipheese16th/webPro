package com.lec.ex1_string;

import java.util.Scanner;

public class Ex05_JuminNo {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String jumin;
		
		//���1. char�� ����
		char temp;
		System.out.println("�ֹι�ȣ�� �Է��ϼ��� : ");	//931101-1155222   index7
		jumin = scanner.next();
		temp = jumin.charAt(7);
		System.out.println((temp=='1' || temp=='3')?"�����Դϴ�": (temp=='2' || temp=='4')?"�����Դϴ�":"�����̼���?");
	
		//���2. String���� ����
		String genderStr = jumin.substring(7,8);
		if(genderStr.equals("1")||genderStr.equals("3")) {
			System.out.println("�����̽ñ���");
		}else if(genderStr.equals("2")||genderStr.equals("4")) {
			System.out.println("�����̽ñ���");
		}else {
			System.out.println("��ȿ���� ���� �ֹι�ȣ�Դϴ�");
		}
		
		//���3. genderStr�� ������ ��ȯ
		int genderInt = Integer.parseInt(genderStr);	//������Ʈ���� ������ ("2"�� 2��)
		if(genderInt==1||genderInt==3) {
			System.out.println("�����̽ñ���");
		}else if(genderInt==2||genderInt==4) {
			System.out.println("�����̽ñ���");
		}else {
			System.out.println("��ȿ���� ���� �ֹι�ȣ�Դϴ�");
		}
		
		
		scanner.close();
	}
	
}

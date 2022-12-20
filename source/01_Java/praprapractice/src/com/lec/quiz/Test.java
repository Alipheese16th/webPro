package com.lec.quiz;

import java.util.ArrayList;
import java.util.Scanner;

public class Test {

	public static void main(String[] args) {
		
		ArrayList<Friend> friends = new ArrayList<Friend>();
		friends.add(new Friend("ȫ�浿", "010-9999-1234", "����� ��걸", "93-05-22"));
		friends.add(new Friend("�ű浿", "010-9999-9999", "����� ������", "94-04-05"));
		friends.add(new Friend("������", "010-8888-8888", "��õ�� ������", "95-12-05"));
		friends.add(new Friend("�̸���", "010-7777-7777", "��õ�� ���̱�", "96-11-05"));
		
		Scanner scanner = new Scanner(System.in);
		
		while(true) {
			System.out.print("�˻��� �ּ� �� ���� 2�ڸ� (��, ���Ḧ ���Ͻø� X�� �Է��Ͻÿ�) :");
			String answer = scanner.next();
			
			if(answer.equalsIgnoreCase("x")) {
				break;
			}
			
			boolean check = true;	//üũ�� �Ҹ��𺯼�
			for (int i = 0; i < friends.size(); i++) {
				String temp = friends.get(i).getAddress().substring(0, 2);
				
				if(answer.equals(temp)) {
					System.out.println(friends.get(i));
					check = false;
				}
			}//for
			
			if(check) {
				System.out.println("�ش� ������ ģ���� �����ϴ�");
			}
			System.out.println();
		}//while
		
		scanner.close();
	}//main

}//class

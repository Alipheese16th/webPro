package com.lec.quiz;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class MemberTestMain_outputStream {

	public static void main(String[] args) {
		ArrayList<Member> members = new ArrayList<Member>();
		Date date = new Date();	//����
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
		String today = sdf.format(date);
		Scanner scanner = new Scanner(System.in);
		String answer;
		
		while(true) {
			System.out.print("ȸ�������� �Ͻðڽ��ϱ�? (����:�ƹ� Ű/����:n) :");
			answer = scanner.next();
			if(answer.equalsIgnoreCase("n")) {
				break;
			}
			System.out.print("�̸��� �Է��ϼ��� :");
			String name = scanner.next();
			System.out.print("��ȭ��ȣ�� �Է��ϼ��� :");
			String tel = scanner.next();
			System.out.print("������ �Է��ϼ���(xxxx-xx-xx):");
			String birthday = scanner.next();
			System.out.print("�ּҸ� �Է��ϼ��� :");
			String address = scanner.next();
			
			members.add(new Member(name,tel,birthday,address));	//�迭�� �߰�
			String temp = birthday.substring(birthday.indexOf("-")+1);	//���� ����
			
			
			
			if(sdf.format(date).equals(temp)) {	//���� ���� ���ϰ� ���� ������ ���ٸ�
				System.out.println("���� �����̽ñ��� ���ϵ����!!");
			}
			
		}//while
		
		
		
		OutputStream os = null;
		try {
			os = new FileOutputStream("com/lec/quiz/member.txt");
			
			
			
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		scanner.close();
	}//main

}

package com.lec.quiz;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class MemberTestMain_printWriter {

	public static void main(String[] args) {
		ArrayList<Member> members = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
		Date date = new Date();
		String today = sdf.format(date);
		String answer = null;
		Date birthday = null;
		Scanner scanner = new Scanner(System.in);
		
		while(true){
			System.out.print("ȸ�������� �Ͻðڽ��ϱ�?(����:n) : ");
			answer = scanner.next();
			if(answer.equalsIgnoreCase("n")) {
				break;
			}
			System.out.print("�̸��� �Է��ϼ��� : ");
			String name = scanner.next();
			System.out.print("��ȭ��ȣ�� �Է��ϼ��� : ");
			String tel = scanner.next();
			System.out.print("��������� �Է��ϼ���(xxxx-xx-xx) : ");
			String birth = scanner.next();
			System.out.print("�ּҸ� �Է��ϼ��� : ");
			scanner.nextLine();
			String address = scanner.nextLine();
			//���� ����ȯ
			
			if(birth.contains("-")) {
				int year = Integer.parseInt(birth.substring(0,birth.indexOf("-")));
				int month = Integer.parseInt(birth.substring(birth.indexOf("-")+1, birth.lastIndexOf("-")));
				int day = Integer.parseInt(birth.substring(birth.lastIndexOf("-")+1));
				birthday = new Date(new GregorianCalendar(year,month-1,day).getTimeInMillis());
				
				if(today.equals(sdf.format(birthday))) {
					System.out.println("���� �����̽ñ��� ���ϵ����~!");
				}
			}	
			members.add(new Member(name,tel,birthday,address));//������ ����� �Է¸��ϸ� null��
		}
		
		if(members.isEmpty()) {
			System.out.println("ȸ�������� �����ϴ�");
		}else {
			PrintWriter pw = null;
			try {
				pw = new PrintWriter("src/com/lec/quiz/member.txt");
				
				System.out.println("�̸�\t��ȭ��ȣ\t����\t�ּ�");
				for (int i = 0; i < members.size(); i++) {
					System.out.print(members.get(i));
					pw.print(members.get(i));
				}
				System.out.println("...���� "+members.size()+"�� ����");
				pw.println("...���� "+members.size()+"�� ����");
				
			} catch (FileNotFoundException e) {
				System.out.println(e.getMessage());
				
			} finally {
				pw.close();
			}
		}
		scanner.close();
	}//main

}

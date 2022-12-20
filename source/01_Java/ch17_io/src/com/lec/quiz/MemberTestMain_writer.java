package com.lec.quiz;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.StringTokenizer;

public class MemberTestMain_writer {

	public static void main(String[] args) {
		ArrayList<Member> members = new ArrayList<>();
		Scanner scanner = new Scanner(System.in);
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
		Date date = new Date();
		String today = sdf.format(date);
		String answer = "start";
		
		while(!(answer.equalsIgnoreCase("n"))) {
			System.out.print("ȸ�������� �Ͻðڽ��ϱ�? (y/n):");
			answer = scanner.next();
			
			if(answer.equalsIgnoreCase("y")) {
				Member member = new Member();
				System.out.print("�̸��� �Է��ϼ���");
				member.setName(scanner.next());
				System.out.print("��ȭ��ȣ�� �Է��ϼ���");
				member.setTel(scanner.next());
				System.out.print("�ּҸ� �Է��ϼ���");
				scanner.nextLine();
				member.setAddress(scanner.nextLine());
				
				System.out.print("������ �Է��ϼ���");
				String birth = scanner.next();
				StringTokenizer st = new StringTokenizer(birth, "-");
				if(st.countTokens()==3) {
					int year = Integer.parseInt(st.nextToken());
					int month = Integer.parseInt(st.nextToken());
					int day = Integer.parseInt(st.nextToken());
					member.setBirthday(new Date(new GregorianCalendar(year,month-1,day).getTimeInMillis()));
					System.out.println(today);
					System.out.println(sdf.format(member.getBirthday()));
					if(today.equals(sdf.format(member.getBirthday()))) {
						System.out.println("���� �����̽ñ��� ���ϵ����!!");
					}
					
					members.add(member);
				}else {
					System.out.println("������ �߸� �Է��ϼ̽��ϴ�. ����");
				}
				
			}//answer y
		}//while
		
		if(members.size()==0) {
			System.out.println("������ ȸ���� �����ϴ�");
		}else {
			Writer writer = null;
			try {
				writer = new FileWriter("src/com/lec/quiz/member.txt",true);
				for(Member member : members) {
					writer.write(member.toString());
					System.out.println(member);
				}
				writer.write("���� "+members.size()+"�� ����\n");
				System.out.println("���� "+members.size()+"�� ����");
				
			} catch (IOException e) {
				System.out.println(e.getMessage());
				
			} finally {
				try {
					if(writer != null) writer.close();
					
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}
		}//������ȸ���� �������� ����Ǵ� ��¹�
		scanner.close();
	}//main

}

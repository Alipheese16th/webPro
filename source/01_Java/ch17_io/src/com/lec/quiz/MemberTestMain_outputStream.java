package com.lec.quiz;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class MemberTestMain_outputStream {

	public static void main(String[] args) {
		ArrayList<Member> members = new ArrayList<Member>();
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
		String today = sdf.format(date);
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.print("ȸ�������� �Ͻðڽ��ϱ�? (����:�ƹ� Ű/����:n) :");
			String answer = scanner.next();
			if (answer.equalsIgnoreCase("n")) {
				break;
			}
			System.out.print("�̸��� �Է��ϼ��� :");
			String name = scanner.next();
			System.out.print("��ȭ��ȣ�� �Է��ϼ��� :");
			String tel = scanner.next();
			System.out.print("��������� �Է��ϼ���(xxxx-xx-xx):");
			String birthday = scanner.next();
			scanner.nextLine();
			System.out.print("�ּҸ� �Է��ϼ��� :");
			String address = scanner.nextLine();
			
			// string ��������� �ɰ��� ����Ʈ���Ŀ� ���
			String[] br = birthday.split("-");
			Date birth;
			
			if(br.length == 3) {
				int year 	= Integer.parseInt(br[0]);
				int month 	= Integer.parseInt(br[1]);
				int day 	= Integer.parseInt(br[2]);
				birth = new Date(new GregorianCalendar(year, month-1, day).getTimeInMillis());
				
			}else {
				System.out.println("��������� �߸��Է��ϼ̽��ϴ�. ó������ �ٽ� �Է��ϼ���");
				continue;
			}
			
			if (sdf.format(birth).equals(today)) {
				System.out.println("���ϵ���� ���� �����̽ñ���?");
			}
			members.add(new Member(name, tel, birth, address)); // �迭�� �߰�

		} // while
		
		if(members.isEmpty()) {
			System.out.println("������ ȸ���� �����ϴ�");
		}else {
			OutputStream os = null;
			try {
				os = new FileOutputStream("src/com/lec/quiz/member.txt");
				
				System.out.println("�̸�\t��ȭ��ȣ\t����\t�ּ�");
				for (int i = 0; i < members.size(); i++) {
					os.write(members.get(i).toString().getBytes());
					System.out.print(members.get(i));
				}
				String mamuri = "....���� "+members.size()+"�� ����\n";
				os.write(mamuri.getBytes());
				System.out.print(mamuri);

			} catch (IOException e) {
				System.out.println(e.getMessage());
				
			} finally {
				try {
					if (os != null)
						os.close();
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}//finally
		
		}//if members is Empty? else
		
		scanner.close();
	}// main

}

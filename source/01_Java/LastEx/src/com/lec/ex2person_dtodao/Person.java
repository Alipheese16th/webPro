package com.lec.ex2person_dtodao;

import java.util.ArrayList;
import java.util.Scanner;

public class Person {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		PersonDao dao = PersonDao.getInstance();
		String fn;
		ArrayList<String> jobs = dao.jnamelist();
		
		do {
			System.out.print("1:�Է� || 2:��������ȸ || 3:��ü��ȸ || �׿�:����");
			fn = sc.next();
			
			switch(fn) {
			case "1" : // �̸�, ������(jobs), ��,��, �� �޾� insert
				System.out.print("�̸��� �Է��ϼ���");
				String pname = sc.next();
				System.out.print("������ �Է��ϼ��� ���:"+jobs);
				String jname = sc.next();
				System.out.print("���������� �Է��ϼ���");
				int kor = sc.nextInt();
				System.out.print("���������� �Է��ϼ���");
				int eng = sc.nextInt();
				System.out.print("���������� �Է��ϼ���");
				int mat = sc.nextInt();
				dao.insertPerson(new PersonDto(pname,jname,kor,eng,mat));
				break;
				
			case "2": // ������޾� ���� ���
				System.out.print("��ȸ�� �������� �����ּ���");
				jname = sc.next();
				ArrayList<PersonDto> dtos = dao.selectJname(jname);
				if(dtos.isEmpty()) {
					System.out.println(jname+" �������� ����� �����ϴ�");
				} else {
					for(PersonDto d : dtos) {
						System.out.println(d);
					}
				}
				break;
				
			case "3":
				dtos = dao.selectAll();
				if(dtos.size()==0) {
					System.out.println("��ϵ� ����� �����ϴ�");
				} else {
					for(int i=0; i<dtos.size();i++) {
						System.out.println(dtos.get(i));
					}
				}
				break;
				
			}//switch
		}while(fn.equals("1") || fn.equals("2") || fn.equals("3"));
		System.out.println("BYE");
		
		sc.close();
	}//main
}
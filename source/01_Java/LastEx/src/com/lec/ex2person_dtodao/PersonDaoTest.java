package com.lec.ex2person_dtodao;

import java.util.ArrayList;

public class PersonDaoTest {

	public static void main(String[] args) {
		PersonDao dao = PersonDao.getInstance();
		System.out.println("----------------1�� insert �׽�Ʈ -----------------");
		PersonDto dto = new PersonDto("ȫ�浿","���",90,95,99);
		dao.insertPerson(dto);
		
		System.out.println("----------------2�� ������ ��� �׽�Ʈ -----------------");
		
		ArrayList<PersonDto> dtos = dao.selectJname("����");
		
		if(dtos.isEmpty()) {
			System.out.println("�ش� ������ ����� ��ϵ��� �ʾҽ��ϴ�");
		} else {
			for(PersonDto d : dtos) {
				System.out.println(d);
			}
		}
		System.out.println("���׸� �˻����");
		dtos = dao.selectJname("���׸�");
		if(dtos.size()!=0) {
			for (int i = 0; i < dtos.size(); i++) {
				System.out.println(dtos.get(i));
				if(i%3==2) {
					System.out.println();
				}
			}
		} else {
			System.out.println("���׸� �������� ����� ��ϵ��� �ʾҽ��ϴ�");
		}
		
		System.out.println("------------------3�� ��� �׽�Ʈ ----------------------");
		dtos = dao.selectAll();
		if(dtos.isEmpty()) {
			System.out.println("��ϵ� ����� �����ϴ�");
		} else {
			for(PersonDto d : dtos) {
				System.out.println(d);
			}
		}
		System.out.println("������ : "+dao.jnamelist());
		
	}

}
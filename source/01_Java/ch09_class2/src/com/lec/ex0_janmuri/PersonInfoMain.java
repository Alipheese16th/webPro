package com.lec.ex0_janmuri;
// PersonInfoMain.java => PersonInfoMain Ŭ������ public���� ����Ǿ�� ��
// �ڹ����Ͽ��� public Ŭ������ �Ѱ��� �־�� �Ѵ�.	main�Լ��� �ִ� Ŭ������ �ݵ�� public

class PersonInfo{
	private String name;
	private int age;
	private char gender;
	public PersonInfo(String name, int age, char gender) {
		super();
		this.name = name;
		this.age = age;
		this.gender = gender;
	}
	public void print() {
		System.out.println("�̸� : "+name+", ���� : "+age+", ���� : "+gender);
	}
	
}//class �̷��� ����°� ��� ���� �ʴ�.

public class PersonInfoMain {
	public static void main(String[] args) {
		PersonInfo[] person = {new PersonInfo("ȫ",25,'f'),
								new PersonInfo("��",24,'m')
								};
		for(PersonInfo p : person) {
			p.print();
			
		}
		
		
	}//main
		
}//p.class


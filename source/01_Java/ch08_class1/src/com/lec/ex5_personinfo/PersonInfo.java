package com.lec.ex5_personinfo;
//new PersonInfo("ȫ�浿", 20, 'm')
public class PersonInfo {
	
	private String name;
	private int age;
	private char gender; // 'm' or 'f'
	
	public PersonInfo() {}
	
	public PersonInfo(String name,int age,char gender) {
		this.name=name;
		this.age=age;
		this.gender=gender;
	}
	
	public void print() {
		System.out.println("�̸� : "+name);
		System.out.println("���� : "+age);
		System.out.println("���� : "+(gender=='m'?"����":(gender=='f'?"����":"��")));
	}
	
	public String infoString() {
		String result = "�̸� : "+name;  //+"\n���� : "+age+"\n���� : "+....
		result += "\n���� : "+age;
		result += "\n���� : "+(gender=='m'?"����":(gender=='f'?"����":"��"));
		return result;
	}
	
}
package com.lec.ex_quiz;
// �л�ī��Ʈ, �� �߰�
public class Student extends Person {
	
	private String ban;
	private static int studentCount;

	public Student(String id, String name, String ban) {
		super(id,name);
		this.ban = ban;
		setNo("student" + ++studentCount);
	}
	
	@Override
	public void print() {
		super.print();
		System.out.print
		("\t(��)"+ban+"\n");
	}
	
	@Override
	public String infoString() {
		return super.infoString() +"\t(��)"+ban;
	}
	
	
	
}

package com.lec.ex_quiz;
// 학생카운트, 반 추가
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
		("\t(반)"+ban+"\n");
	}
	
	@Override
	public String infoString() {
		return super.infoString() +"\t(반)"+ban;
	}
	
	
	
}

package com.lec.ex_quiz;
// 강사카운트 , 과목 추가
public class Gangsa extends Person {
	
	private String subject;
	private static int gangsaCount;

	public Gangsa(String id, String name, String subject) {
		super(id,name);
		this.subject = subject;
		setNo("lecturer"+ ++gangsaCount);
	}
	
	@Override
	public void print() {
		super.print();
		System.out.print
		("\t(과목)"+subject+"\n");
	}
	
	@Override
	public String infoString() {
		return super.infoString()+"\t(과목)"+subject;
	}
	
	
	
}

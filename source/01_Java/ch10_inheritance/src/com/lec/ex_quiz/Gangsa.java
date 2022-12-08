package com.lec.ex_quiz;
// ����ī��Ʈ , ���� �߰�
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
		("\t(����)"+subject+"\n");
	}
	
	@Override
	public String infoString() {
		return super.infoString()+"\t(����)"+subject;
	}
	
	
	
}

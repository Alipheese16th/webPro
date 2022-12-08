package com.lec.ex_quiz;
// ������ī��Ʈ, �μ� �߰�
public class Staff extends Person {
	
	private String department;
	private static int staffCount;

	public Staff(String id, String name, String department) {
		super(id,name);
		this.department = department;
		setNo("staff"+ ++staffCount);
	}
	
	@Override
	public void print() {
		super.print();
		System.out.print
		("\t(�μ�)"+department+"\n");
	}
	
	@Override
	public String infoString() {
		return super.infoString()+"\t(�μ�)"+department;
	}
	
	
}

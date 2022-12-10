package com.lec.ex_quiz;
// 스태프카운트, 부서 추가
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
		("\t(부서)"+department+"\n");
	}
	
	@Override
	public String infoString() {
		return super.infoString()+"\t(부서)"+department;
	}
	
	
}

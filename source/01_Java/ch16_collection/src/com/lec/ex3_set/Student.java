package com.lec.ex3_set;

public class Student {
	private int grade;
	private String name;
	
	public Student(int grade, String name) {
		this.grade = grade;
		this.name = name;
	}

	@Override
	public String toString() {
		return grade + "�г� " + name;
	}
	
	@Override
	public int hashCode() {
		return toString().hashCode();	//���� grade�� ���� name�� ��� toString()����� ���� ���� hashCode
	}
	
	@Override
	public boolean equals(Object obj) {	//s1.equals(s2) : s1�� this, s2�� obj
		if(obj!=null&&getClass() == obj.getClass()) {
			Student other = (Student)obj;
			return grade==other.grade && name==other.name;
		}
		return false;
	}
	
}
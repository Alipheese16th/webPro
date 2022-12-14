package com.lec.ex4_object;

public class Person {
	private long juminNo; //9311011155222

	public Person() {}

	public Person(long juminNo) {
		this.juminNo = juminNo;
	}
	
	public String toString() {
		return "주민번호는 "+juminNo;
	}
	
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
	
}

package com.lec.ex0_janmuri;
// PersonInfoMain.java => PersonInfoMain 클래스가 public으로 선언되어야 함
// 자바파일에는 public 클래스가 한개만 있어야 한다.	main함수가 있는 클래스는 반드시 public

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
		System.out.println("이름 : "+name+", 나이 : "+age+", 성별 : "+gender);
	}
	
}//class 이렇게 만드는건 사실 좋지 않다.

public class PersonInfoMain {
	public static void main(String[] args) {
		
		PersonInfo[] person = {
				new PersonInfo("홍",25,'f'),
				new PersonInfo("김",24,'m')
		};
		
		for(PersonInfo p : person) {
			p.print();
		}
		
		
	}//main
		
}//p.class



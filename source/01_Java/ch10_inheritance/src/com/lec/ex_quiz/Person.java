package com.lec.ex_quiz;
//번호, 아이디, 이름
public class Person {
	
	private String no;
	private String id;
	private String name;
	
	public Person(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public void print() {
		System.out.printf("(번호)%s\t(ID)%s\t(이름)%s",no,id,name);
	}
	
	public String infoString() {
		return String.format("(번호)%s\t(ID)%s\t(이름)%s",no,id,name);
	}

	//getter & setter


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	

}

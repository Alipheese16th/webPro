package com.lec.ex;

public class Student {
	private String stuId;
	private String name;
	private int grade;
	private char group;
	private int score;
	
	public Student() {}
	public Student(String stuId, String name, int grade, char group, int score) {
		this.stuId = stuId;
		this.name = name;
		this.grade = grade;
		this.group = group;
		this.score = score;
	}
	public String getStuId() {
		return stuId;
	}
	public void setStuId(String stuId) {
		this.stuId = stuId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public char getGroup() {
		return group;
	}
	public void setGroup(char group) {
		this.group = group;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	
}

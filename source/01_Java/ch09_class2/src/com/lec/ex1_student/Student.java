package com.lec.ex1_student;

// 单捞磐 : name(String), kor(int), eng(int), mat(int), tot(int), avg(double)
// 皋家靛 : infoString(), infoPrint()
// Student st = new Student();

public class Student {
	
	private String name ;
	private int kor, eng, mat, tot ;
	private double avg ;
	
	public Student() {}
	public Student(String name,int kor,int eng,int mat) {
		this.name = name ;
		this.kor = kor ;
		this.eng = eng ;
		this.mat = mat ;
		tot = kor + eng + mat ;
		avg = tot / 3.0 ;
	}
	
	//Student s = new Student("沥快己",100,100,100);
	//Sytem.out.println(s.infoString()) => \t沥快己\t100\t100\t100\t300\t100.0
	public String infoString() {
		return String.format("\t%s\t%d\t%d\t%d\t%d\t%.1f\n",name,kor,eng,mat,tot,avg);
	}
	
	public void infoPrint() {
		System.out.printf("\t%s\t%d\t%d\t%d\t%d\t%.1f\n",name,kor,eng,mat,tot,avg);
	}
	//setter
	public void setName(String name) {
		this.name = name;
	}
	public void setKor(int kor) {
		this.kor = kor;
	}
	public void setEng(int eng) {
		this.eng = eng;
	}
	public void setMat(int mat) {
		this.mat = mat;
	}
	//getter
	public String getName() {
		return name;
	}
	public int getKor() {
		return kor;
	}
	public int getEng() {
		return eng;
	}
	public int getMat() {
		return mat;
	}
	public int getTot() {
		return tot;
	}
	public double getAvg() {
		return avg;
	}
	
}

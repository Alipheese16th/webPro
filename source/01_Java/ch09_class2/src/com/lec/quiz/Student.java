package com.lec.quiz;
// 객체 생성 : new Student("정우성",90,80,100); -> 번호, 총점, 평균도 같이 초기화 
public class Student {
	private String name;
	private int kor,eng,mat;
	private int tot,avg;
	private int number;
	private static int count;
	
	public Student() {}
	public Student(String name,int kor,int eng,int mat) {
		this.name = name;
		this.kor = kor;
		this.eng = eng;
		this.mat = mat;
		this.tot = kor+eng+mat;
		this.avg = (int)Math.round((kor+eng+mat)/3.0);
		this.number = ++count;
	}
	
	public String infoString() {
		return String.format("  %d\t%s\t%d\t%d\t%d\t%d\t%d\n",number,name,kor,eng,mat,tot,avg);
	}
	public void infoPrint() {
		System.out.printf("  %d\t%s\t%d\t%d\t%d\t%d\t%d\n",number,name,kor,eng,mat,tot,avg);
	}

	
	//getter & setter
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getKor() {
		return kor;
	}

	public void setKor(int kor) {
		this.kor = kor;
	}

	public int getEng() {
		return eng;
	}

	public void setEng(int eng) {
		this.eng = eng;
	}

	public int getMat() {
		return mat;
	}

	public void setMat(int mat) {
		this.mat = mat;
	}

	public int getTot() {
		return tot;
	}

	public void setTot(int tot) {
		this.tot = tot;
	}

	public int getAvg() {
		return avg;
	}

	public void setAvg(int avg) {
		this.avg = avg;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
}

package com.lec.ex2_date;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class Sawon2 {
	
	private String no;
	private String name;
	private Dept dept;
	private Date date;
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy��M��d��");
	
	public Sawon2() {}
	
	public Sawon2(String no, String name, Dept dept) {
		this.no = no;
		this.name = name;
		this.dept = dept;
		this.date = new Date();
	}

	public Sawon2(String no, String name, Dept dept, int y, int m , int d) {
		this.no = no;
		this.name = name;
		this.dept = dept;
//		GregorianCalendar gc = new GregorianCalendar(y,m-1,d);
//		date = gc.getTime();
		date = new Date(new GregorianCalendar(y,m-1,d).getTimeInMillis());
	} 
	
	public String toString() {
		if((dept+"").length()>10)
			return "[���]"+no+"\t\t[�̸�]"+name+"\t[�μ�]"+dept+"\t[�Ի���]"+sdf.format(date);
		else
			return "[���]"+no+"\t\t[�̸�]"+name+"\t[�μ�]"+dept+"\t\t[�Ի���]"+sdf.format(date);
	}
	
	
}

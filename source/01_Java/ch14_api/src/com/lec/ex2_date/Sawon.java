package com.lec.ex2_date;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class Sawon {
	
	public static final String COMPUTER = "COMPUTER";
	public static final String PLANNING = "PLANNING";
	public static final String DESIGN = "DESIGN";
	public static final String ACCOUNTING = "ACCOUNTING";
	public static final String HUMANRESOURCES = "HUMANRESOURCES";
	
	private String no;
	private String name;
	private String dept;
	private Date date;
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy년M월d일");
	
	public Sawon() {}
	
	public Sawon(String no, String name, String dept) {
		this.no = no;
		this.name = name;
		this.dept = dept;
		this.date = new Date();
	}

	public Sawon(String no, String name, String dept, int y, int m , int d) {
		this.no = no;
		this.name = name;
		this.dept = dept;
//		GregorianCalendar gc = new GregorianCalendar(y,m-1,d);
//		date = gc.getTime();
		date = new Date(new GregorianCalendar(y,m-1,d).getTimeInMillis());
	} 
	
	public String toString() {
		if(dept.length()>10)
			return "[사번]"+no+"\t\t[이름]"+name+"\t[부서]"+dept+"\t[입사일]"+sdf.format(date);
		else
			return "[사번]"+no+"\t\t[이름]"+name+"\t[부서]"+dept+"\t\t[입사일]"+sdf.format(date);
	}
	
	
}

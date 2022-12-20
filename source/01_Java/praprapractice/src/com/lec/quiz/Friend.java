package com.lec.quiz;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class Friend {
	private String 	name;
	private String 	phone;
	private String 	address;
	private Date 	birthday;
	
	public Friend(String name, String phone, String address, String birthday) {
		this.name 	 = name;
		this.phone 	 = phone;
		this.address = address;
		
		//���ڿ��� �Է¹ް� Date�������� ���� ��ȯ
		int year  = Integer.parseInt(birthday.substring(0, birthday.indexOf("-")));
		int month = Integer.parseInt(birthday.substring(birthday.indexOf("-")+1,birthday.lastIndexOf("-")));
		int day	  = Integer.parseInt(birthday.substring(birthday.lastIndexOf("-")+1));
		this.birthday = new Date(new GregorianCalendar(year,month-1,day).getTimeInMillis());
	}
	
	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
		return 	  "\n�̸� : "  	+ name
				+ "\n�ּ� : "		+ address
				+ "\n�ڵ��� : "	+ phone
				+ "\n �� �� : "	+ sdf.format(birthday);
	}
	
	//getter & setter 
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	
}

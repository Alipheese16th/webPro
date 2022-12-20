package com.lec.quiz;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class Member {
	private String name;
	private String tel;
	private Date birthday;
	private String address;
	
	public Member(String name, String tel, String birthday, String address) {
		this.name = name;
		this.tel = tel;
		this.address = address;
		
		String[] br = birthday.split("-");
		this.birthday = new Date(new GregorianCalendar(
				Integer.parseInt(br[0]),
				Integer.parseInt(br[1]),
				Integer.parseInt(br[2])
		).getTimeInMillis());
		
	}

	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
		return name + " " + tel + " " + sdf.format(birthday) + " " + address+"\n";
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getBirthday() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
		return sdf.format(birthday);
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
	
	
}

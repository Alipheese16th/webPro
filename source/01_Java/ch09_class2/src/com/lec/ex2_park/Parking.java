package com.lec.ex2_park;

import com.lec.constant.Constant;

public class Parking {
	
	private String no;
	private int inTime,outTime,fee;
//	private static final int HOURLYPARKINGRATE = 2000; //상수(final 변수)
	
	public Parking() {}
	public Parking(String no,int inTime) {
		this.inTime = inTime;
		this.no = no;
		System.out.println("\""+no+"\""+"님 어서오세요.");
		System.out.println("입차시간 :"+inTime+"시");
		System.out.println("본 주차장의 요금은 시간당 2천원입니다.\n");
	}
	
	public void out(int outTime) {
		this.outTime = outTime;
		System.out.println("\""+no+"\""+"님 안녕히 가세요.");
		System.out.println("입차시간 :"+inTime+"시");
		System.out.println("출차시간 :"+outTime+"시");
		System.out.println("주차요금 :"+count(inTime,this.outTime)+"원\n");
		fee = count(inTime,this.outTime);
	}
	
	private static int count(int inTime,int outTime) {
		return (outTime - inTime)*Constant.HOURLYPARKINGRATE;  //주차요금 시간당 2천원
	}
	public String getNo() {
		return no;
	}
	public int getInTime() {
		return inTime;
	}
	public int getOutTime() {
		return outTime;
	}
	public int getFee() {
		return fee;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public void setInTime(int inTime) {
		this.inTime = inTime;
	}
	public void setOutTime(int outTime) {
		this.outTime = outTime;
	}
	public void setFee(int fee) {
		this.fee = fee;
	}
	
}

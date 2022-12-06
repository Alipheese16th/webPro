package com.lec.ex2_park;

import com.lec.constant.Constant;

public class Parking {
	
	private String no;
	private int inTime,outTime,fee;
//	private static final int HOURLYPARKINGRATE = 2000; //���(final ����)
	
	public Parking() {}
	public Parking(String no,int inTime) {
		this.inTime = inTime;
		this.no = no;
		System.out.println("\""+no+"\""+"�� �������.");
		System.out.println("�����ð� :"+inTime+"��");
		System.out.println("�� �������� ����� �ð��� 2õ���Դϴ�.\n");
	}
	
	public void out(int outTime) {
		this.outTime = outTime;
		System.out.println("\""+no+"\""+"�� �ȳ��� ������.");
		System.out.println("�����ð� :"+inTime+"��");
		System.out.println("�����ð� :"+outTime+"��");
		System.out.println("������� :"+count(inTime,this.outTime)+"��\n");
		fee = count(inTime,this.outTime);
	}
	
	private static int count(int inTime,int outTime) {
		return (outTime - inTime)*Constant.HOURLYPARKINGRATE;  //������� �ð��� 2õ��
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

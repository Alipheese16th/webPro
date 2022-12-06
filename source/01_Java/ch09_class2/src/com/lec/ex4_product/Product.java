package com.lec.ex4_product;

public class Product {
	private int serialNo; //객체 고유 번호
	public static int count=100; //공유변수=static변수=클래스변수
	public Product() {
		serialNo = ++count;
	}
	
	public static void staticMethod() {
		System.out.println("static 메소드test");
	}
	
	public void infoPrint() {
		System.out.println("시리얼번호 : "+serialNo+"\t공유변수 count : "+count);
	}
	
	public int getSerialNo() {
		return serialNo;
	}
	
	public void setSerialNo(int serialNo) {
		this.serialNo = serialNo;
	}
	
}

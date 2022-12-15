package com.lec.ex4_object;
// (1)implements Cloneable
// (2)복제 메소드 clone을 오버라이드
public class Rectangle implements Cloneable {
	
	private int width;
	private int height;
	private String color;
	
	public Rectangle() {
		color = "검정";
	}
	public Rectangle(String color) {
		this.color = color;
	}
	public Rectangle(int width, int height) {
		this.width = width;
		this.height = height;
		color = "검정";
	}
	public Rectangle(int width, int height, String color) {
		this.width = width;
		this.height = height;
		this.color = color;
	}

	@Override
	public String toString() {
		//return "가로 " + width + "cm, 세로 " + height + "cm의 " + color + "색 사각형";
		return String.format("가로 %dcm 세로 %dcm의 %s색 사각형\n",width,height,color);
	}
	
	@Override
	public boolean equals(Object obj) { //r1.equals(r2) => r1이 this, r2가 obj
		if(obj!=null && obj.getClass()==getClass()) {
			Rectangle other = (Rectangle)obj;
			boolean widthchk = width == other.width;
			boolean heightchk = height == other.height;
			boolean colorchk = color.equals(other.color);
			return widthchk && heightchk && colorchk;
		}
		return false;
	}
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
}

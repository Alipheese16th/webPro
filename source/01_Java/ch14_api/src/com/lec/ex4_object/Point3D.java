package com.lec.ex4_object;

public class Point3D implements Cloneable{
	
	private double x;
	private double y;
	private double z;
	
	public Point3D() {}

	public Point3D(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public String toString() {
		return "ÁÂÇ¥°ª [x="+x+", y="+y+", z="+z+"]";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj!=null&& obj instanceof Point3D) {
			Point3D other = (Point3D) obj;
			return x==other.x && y==other.y && z==other.z;
		}else {
			return false;
		}
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
}

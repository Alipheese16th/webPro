package com.lec.ex4_object;

public class QuizPoint3Dmain {
	public static void main(String[] args) throws CloneNotSupportedException {
		
		Point3D p1 = new Point3D(); //0.0
		Point3D p2 = new Point3D(1,2,3);
		Point3D p3 = new Point3D(1,2,3);
		Point3D p4 = new Point3D(4,5,6);
		Point3D[] ps = {p1,p2,p3,p4};
		
		for(Point3D p : ps ) {	//toString
			System.out.println(p);
		}
		
		System.out.println("p1와 p2의 좌표값이 "+(p1.equals(p2)?"같다":"다르다"));//equals
		System.out.println("p2와 p3의 좌표값이 "+(p2.equals(p3)?"같다":"다르다"));
		
		Point3D p5 = (Point3D) p4.clone(); //클론
		System.out.println("클론이 "+((p4!=p5&&p4.equals(p5))?"맞다":"아니다"));
		
	}
}

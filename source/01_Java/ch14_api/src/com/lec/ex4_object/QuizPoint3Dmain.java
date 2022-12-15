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
		
		System.out.println("p1�� p2�� ��ǥ���� "+(p1.equals(p2)?"����":"�ٸ���"));//equals
		System.out.println("p2�� p3�� ��ǥ���� "+(p2.equals(p3)?"����":"�ٸ���"));
		
		Point3D p5 = (Point3D) p4.clone(); //Ŭ��
		System.out.println("Ŭ���� "+((p4!=p5&&p4.equals(p5))?"�´�":"�ƴϴ�"));
		
	}
}

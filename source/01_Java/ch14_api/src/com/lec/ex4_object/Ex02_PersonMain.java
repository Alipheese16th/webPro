package com.lec.ex4_object;

public class Ex02_PersonMain {

	public static void main(String[] args) {
		
		Person p1 = new Person(9812121045215L);
		Person p2 = new Person(9812121045215L);
		Person p3 = null;
		Friend f = new Friend();
		
		System.out.println(p1.equals(p2)?"p1�� p2�� ����":"p1�� p2�� �ٸ���");
		System.out.println(p1==p2?"p1�� p2�� ���� �ּ� ����":"p1�� p2�� �ٸ� �ּ� ����");
		System.out.println(p1.equals(p3)?"p1�� p3�� ����":"p1�� p3�� �ٸ���");
		System.out.println(p1.equals(f)?"p1�� f�� ����":"p1�� f�� �ٸ���");
		
		System.out.println(p1.getClass());
		System.out.println(p2.getClass());
		System.out.println(f.getClass());
	}

}
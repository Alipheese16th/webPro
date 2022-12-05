package com.lec.ex1_square;

//Ŭ���� ���� : �Ӽ�, ������, �޼ҵ�, [getter&setter](�ʿ��ϸ龲�� �ƴϸ�Ⱦ���)
//Square s = new Square(); //s�� ���̵�� 0���� �ʱ�ȭ
//Square s1 = new Square(10); //s�� ���̵带 10���� �ʱ�ȭ�ϸ鼭 ��ü ���� 
public class Square {
	private int side;

	public Square() {// ������ �Լ�: return type�� ���� Ŭ������� ���� �Լ�
		System.out.println("�Ű����� ���� ������ �Լ� ȣ��");
	}

	public Square(int side) { // �Ű������� �ִ� ������ �Լ� �뵵 : ������ �ʱ�ȭ
		this.side = side;
		System.out.println("�Ű����� �ִ� ������ �Լ� ȣ��");
	}

	public int area() {
		return side * side;
	}

	// setter : s1.setSide(5);
	public void setSide(int side) {
		this.side = side;
	}

	// getter : sysout(s1.getSide)
	public int getSide() {
		return side;
	}

}

package com.lec.ex5_car;
// LowGradeCar low = new LowGradeCar("����", "�Ϲ�", 1000, "�Ŀ�")
// low.getSpec();
public class LowGradeCar extends Car {
	
	private int tax;

	public LowGradeCar(String color, String tire, int displacement, String handle) {
		super(color, tire, displacement, handle);
	}

	@Override
	public void getSpec() {// tax�� ������ Spec�� ���
		if(getDisplacement() > 900) {
			tax += ( getDisplacement() - 900 ) * 500;
		}
		System.out.println("=======================================");
		System.out.println("���� : " + getColor());
		System.out.println("Ÿ�̾� : " + getTire());
		System.out.println("��ⷮ : " + getDisplacement());
		System.out.println("�� ��  : " + getHandle());
		System.out.println("�� �� : " + tax);
		System.out.println("=======================================");
	}
	
}
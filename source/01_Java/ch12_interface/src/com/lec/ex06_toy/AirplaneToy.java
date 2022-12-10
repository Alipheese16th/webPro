package com.lec.ex06_toy;

public class AirplaneToy implements IMissile, ILight {

	
	
	public AirplaneToy() {
		System.out.println("������Դϴ�.");
		canLight();
		canMissile();
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}

	@Override
	public void canLight() {
		System.out.println("�Һ��߻� �����մϴ�.");
	}

	@Override
	public void canMissile() {
		System.out.println("�̻����� �߻��� �� �ֽ��ϴ�.");
	}

	@Override
	public void play() {
		canLight(); canMissile();
	}

}

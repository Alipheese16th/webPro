package com.lec.ex5_car;
// HighGradeCar high = new HighGradeCar("빨강", "일반", 2000, "파워")
// High.getSpec();
public class HighGradeCar extends Car {
	
	private int tax;

	public HighGradeCar(String color, String tire, int displacement, String handle) {
		super(color, tire, displacement, handle);
		tax = 100000;
	}

	@Override
	public void getSpec() {// tax를 포함한 Spec들 출력
		
		if(getDisplacement() > 1500) {
			tax += ( getDisplacement() - 1500 ) * 600;
		}
		
		System.out.println("=======================================");
		System.out.println("색상 : " + getColor());
		System.out.println("타이어 : " + getTire());
		System.out.println("배기량 : " + getDisplacement());
		System.out.println("핸 들  : " + getHandle());
		System.out.println("세 금 : " + tax);
		System.out.println("=======================================");
	}
	
}

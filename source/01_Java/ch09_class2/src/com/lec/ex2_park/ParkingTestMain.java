package com.lec.ex2_park;

public class ParkingTestMain {

	public static void main(String[] args) {
		
		Parking car1 = new Parking("111��1111",6);
		Parking car2 = new Parking("222��2222",8);
		
		car1.out(8);
		car2.out(9);
		
		Parking car3 = new Parking("333��3333",9);
		Parking car4 = new Parking("444��4444",12);
		car3.out(15);
		car4.out(20);
		
		
	}
}

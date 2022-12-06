package com.lec.ex2_park;

public class ParkingTestMain {

	public static void main(String[] args) {
		
		Parking car1 = new Parking("111风1111",6);
		Parking car2 = new Parking("222风2222",8);
		
		car1.out(8);
		car2.out(9);
		
		Parking car3 = new Parking("333风3333",9);
		Parking car4 = new Parking("444风4444",12);
		car3.out(15);
		car4.out(20);
		
		
	}
}

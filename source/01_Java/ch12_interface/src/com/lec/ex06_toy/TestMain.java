package com.lec.ex06_toy;

public class TestMain {

	public static void main(String[] args) {
		
		PoohToy pooh = new PoohToy();
		MazingerToy mazinger = new MazingerToy();
		AirplaneToy airplane = new AirplaneToy();
		
		IToy[] toys = {pooh,mazinger,airplane};
		
		for(IToy t : toys) {
			//��ü�� Ŭ���� �̸� ���
			System.out.println(t.getClass().getName());
			t.play();
		}
		
		

	}
}

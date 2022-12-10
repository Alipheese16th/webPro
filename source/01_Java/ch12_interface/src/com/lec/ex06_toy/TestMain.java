package com.lec.ex06_toy;

public class TestMain {

	public static void main(String[] args) {
		
		PoohToy pooh = new PoohToy();
		MazingerToy mazinger = new MazingerToy();
		AirplaneToy airplane = new AirplaneToy();
		
		IToy[] toys = {pooh,mazinger,airplane};
		
		for(IToy t : toys) {
			//객체의 클래스 이름 출력
			System.out.println(t.getClass().getName());
			t.play();
		}
		
		

	}
}

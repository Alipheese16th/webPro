package com.lec.ex04_actor;

public class ActorMain {

	public static void main(String[] args) {
		
		Actor gong = new Actor("공유");
		gong.makePizza();
		gong.outFire();
		gong.canCatchCriminal();
		
		Chef chefGong = gong;
		chefGong.makePizza();
//		chefgong.outFire();
//		chefgong.canCatchCriminal();
		
//		FireFighter firegong = chefgong; // 객체는 같지만 형제타입끼리는 할당 불가능
		FireFighter fireGong = gong;
		fireGong.outFire();
//		firegong.makePizza();
//		firegong.canCatchCriminal();
		
		PoliceMan policeGong = gong;
		policeGong.canCatchCriminal();
//		policeGong.makePizza();
//		policeGong.outFire();
		
		//다형성 예제 
	}
}

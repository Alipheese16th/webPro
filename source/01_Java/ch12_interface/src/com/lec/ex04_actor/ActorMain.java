package com.lec.ex04_actor;

public class ActorMain {

	public static void main(String[] args) {
		
		Actor gong = new Actor("����");
		gong.makePizza();
		gong.outFire();
		gong.canCatchCriminal();
		
		Chef chefGong = gong;
		chefGong.makePizza();
//		chefgong.outFire();
//		chefgong.canCatchCriminal();
		
//		FireFighter firegong = chefgong; // ��ü�� ������ ����Ÿ�Գ����� �Ҵ� �Ұ���
		FireFighter fireGong = gong;
		fireGong.outFire();
//		firegong.makePizza();
//		firegong.canCatchCriminal();
		
		PoliceMan policeGong = gong;
		policeGong.canCatchCriminal();
//		policeGong.makePizza();
//		policeGong.outFire();
		
		//������ ���� 
	}
}

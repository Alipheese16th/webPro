package com.lec.ex2_date;

public class SawonMain {

	public static void main(String[] args) {
		
		Sawon sawon1 = new Sawon("a01","ȫ�浿",Sawon.ACCOUNTING);
		Sawon sawon2 = new Sawon("a02","�ű浿",Sawon.DESIGN,2022,12,15);
		Sawon sawon3 = new Sawon("a03","���浿",Sawon.HUMANRESOURCES,1998,7,7);
		
		Sawon[] sawons = {sawon1,sawon2,sawon3};
		for(Sawon sawon : sawons) {
			System.out.println(sawon);
		}
		
	}
}

package com.lec.ex2_date;

public class SawonMain2 {

	public static void main(String[] args) {
		
		Sawon2 sawon1 = new Sawon2("a01","ȫ�浿",Dept.COMPUTER);
		Sawon2 sawon2 = new Sawon2("a02","�ű浿",Dept.ACCOUNTING,2022,12,15);
		Sawon2 sawon3 = new Sawon2("a03","���浿",Dept.HUMANRESOURCES,1998,7,7);
		
		Sawon2[] sawons = {sawon1,sawon2,sawon3};
		
		for(Sawon2 sawon : sawons) {
			System.out.println(sawon);
		}
		
	}
}

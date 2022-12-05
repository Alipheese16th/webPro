package com.lec.ex3_account;

public class AccountMain {

	public static void main(String[] args) {
		Account hong = new Account("110-1", "홍길동", 1000);
		hong.infoPrint();
		System.out.println(hong.infoString());
		Account hong1 = new Account("110-2","홍길순");
		System.out.println(hong1.infoString());
		Account hong2 = new Account();
		System.out.println(hong2.infoString());
		hong.withdraw(500);
		hong.deposite(5000);
		hong1.deposite(2200000000l);
		hong1.withdraw(2200000001l);  //잔액 부족시 인출 불가 
		
		
		
		
	}
}

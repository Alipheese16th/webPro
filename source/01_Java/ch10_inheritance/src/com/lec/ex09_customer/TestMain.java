package com.lec.ex09_customer;

public class TestMain {

	public static void main(String[] args) {
		
		Customer customer = new Customer("ȫ�浿", "010-9999-9999",  "����� ���빮��", "12-01");
		System.out.println(customer.infoString());
		customer.buy(500000);
		customer.buy(510000);
		
		Staff staff = new Staff("ȫ���", "010-8888-8888", "2022-12-06", "�Ǹź�");
		
		System.out.println("============================================");
		
		Person[] person = {customer, staff};
		
		for(Person p : person) {
			System.out.println(p.infoString());
			
			if(p instanceof Customer) {
				((Customer) p).buy(1000000);
			}
		}
		System.out.println("============================================");
		
		Person customer1 = new Customer("�ű浿",  "010-7777-7777",  "����� ��������", "12-07");
		//customer1.buy //���� Person���� 
		if(customer1 instanceof Customer) {  //Ŭ������ ������� ����ȯ Ȯ�� 
			((Customer)customer1).buy(20000);
		}else {
			System.out.println("buy�Ҽ� ���� ��ü�Դϴ�.");
		}
		
	}
}

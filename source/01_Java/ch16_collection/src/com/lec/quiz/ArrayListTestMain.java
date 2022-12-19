package com.lec.quiz;

import java.util.ArrayList;
import java.util.Scanner;

public class ArrayListTestMain {

	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		ArrayList<Customer> customers = new ArrayList<Customer>();
		
			while(true){
				System.out.print("ȸ�������� �Ͻðڽ��ϱ�? (Y/N)");
				String yn = scanner.next();
				
				if(yn.equalsIgnoreCase("n")) {
					break;
					
				}else if(yn.equalsIgnoreCase("y")){
					System.out.print("�̸��� �Է��ϼ��� : ");
					String name = scanner.next();
					
					System.out.print("��ȭ��ȣ�� �Է��ϼ��� : ");
					String tel = scanner.next();
					
					scanner.nextLine();
					System.out.print("�ּҸ� �Է��ϼ��� : ");
					String address = scanner.nextLine();
					
					customers.add(new Customer(name,tel,address));
					System.out.println(name+"���� ȸ�� ������ �Ϸ�Ǿ����ϴ� �����մϴ�.");
				}
				
			}
		
		if(customers.isEmpty()) {	//���� ȸ������� ���ٸ�
			System.out.println("������ ȸ�� ����� �����ϴ�");
		}else {
			System.out.println("= = = ȸ�� ��� = = =");
			
			for(Customer customer : customers) {
				System.out.println(customer);
			}
		scanner.close();
		}
		
	}//main

}

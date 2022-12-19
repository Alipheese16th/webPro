package com.lec.quiz;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

public class HashMapTestMain {
	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		HashMap<String, Customer> customers = new HashMap<String, Customer>();
		String yn;
		
		do{
			System.out.print("ȸ�������� �Ͻðڽ��ϱ�? (Y/N)");
			yn = scanner.next();
			
			if(yn.equalsIgnoreCase("y")) {
				System.out.print("�̸��� �Է��ϼ��� : ");
				String name = scanner.next();
				
				System.out.print("��ȭ��ȣ�� �Է��ϼ��� : ");
				String tel = scanner.next();
					
				if(customers.get(tel)!=null) {//tel�� Ű�� �����Ѵٸ�. �� �ߺ����� �ִٸ�
						System.out.println("�̹� �Է��� ��ȭ��ȣ�� �����մϴ�. Ȯ�κ�Ź�帳�ϴ�");
							continue;	//�ش� ȸ���� ���������� �ٽ� �ݺ�������
				}
				
				scanner.nextLine();
				System.out.print("�ּҸ� �Է��ϼ��� : ");
				String address = scanner.nextLine();
				
				customers.put(tel,new Customer(name,tel,address));
				System.out.println(name+"���� ȸ�� ������ �Ϸ�Ǿ����ϴ� �����մϴ�.");
			}
		}while(!(yn.equalsIgnoreCase("n")));
		
		if(customers.isEmpty()) {	//���� ȸ������� ���ٸ�
			System.out.println("������ ȸ�� ����� �����ϴ�");
		}else {
			System.out.println("= = = ȸ�� ��� = = =");
			Iterator<String> iterator = customers.keySet().iterator();
			
			while(iterator.hasNext()) {
				String key = iterator.next();
				System.out.println(customers.get(key));
			}
			
		}
		scanner.close();
		
		
	}

}

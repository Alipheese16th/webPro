package com.lec.quiz;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

public class HashSetTestMain {

	public static void main(String[] args) {
	
		Scanner scanner = new Scanner(System.in);
		HashSet<Customer> customers = new HashSet<Customer>();
		String yn;
		
		do{
			System.out.print("ȸ�������� �Ͻðڽ��ϱ�? (Y/N)");
			yn = scanner.next();
			
			if(yn.equalsIgnoreCase("y")) {
				System.out.print("�̸��� �Է��ϼ��� : ");
				String name = scanner.next();
				
				System.out.print("��ȭ��ȣ�� �Է��ϼ��� : ");
				String tel = scanner.next();
				
				scanner.nextLine();
				System.out.print("�ּҸ� �Է��ϼ��� : ");
				String address = scanner.nextLine();
				
				Customer temp = new Customer(name,tel,address);
				Iterator<Customer> iterator1 = customers.iterator();
				
				boolean check = true;	//�ߺ�üũ�� �Ҹ��𺯼�
				
				while(iterator1.hasNext()) {//iterator�� ������ �ߺ��� Ȯ��
					if(temp.equals(iterator1.next())) {
						check = false;
					}
				}
				if(check) {//�ߺ����� ������ true��ȯ
					customers.add(temp);
					System.out.println(name+"���� ȸ�� ������ �Ϸ�Ǿ����ϴ� �����մϴ�.");
				}else {
					System.out.println("�� ����. �ߺ��� ȸ���Դϴ�. ��");
				}
			}
		}while(!(yn.equalsIgnoreCase("n")));
		
		if(customers.isEmpty()) {	//���� ȸ������� ���ٸ�
			System.out.println("������ ȸ�� ����� �����ϴ�");
			
		}else {
			System.out.println("= = = ȸ�� ��� = = =");
			Iterator<Customer> iterator2 = customers.iterator();
			while(iterator2.hasNext()) {
				System.out.println(iterator2.next());
			}
		}
		scanner.close();
		
	}

}

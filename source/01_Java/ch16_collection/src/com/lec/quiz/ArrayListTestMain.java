package com.lec.quiz;

import java.util.ArrayList;
import java.util.Scanner;

public class ArrayListTestMain {

	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		ArrayList<Customer> customers = new ArrayList<Customer>();
		
			while(true){
				System.out.print("회원가입을 하시겠습니까? (Y/N)");
				String yn = scanner.next();
				
				if(yn.equalsIgnoreCase("n")) {
					break;
					
				}else if(yn.equalsIgnoreCase("y")){
					System.out.print("이름을 입력하세요 : ");
					String name = scanner.next();
					
					System.out.print("전화번호를 입력하세요 : ");
					String tel = scanner.next();
					
					scanner.nextLine();
					System.out.print("주소를 입력하세요 : ");
					String address = scanner.nextLine();
					
					customers.add(new Customer(name,tel,address));
					System.out.println(name+"님의 회원 가입이 완료되었습니다 감사합니다.");
				}
				
			}
		
		if(customers.isEmpty()) {	//만약 회원목록이 없다면
			System.out.println("가입한 회원 목록이 없습니다");
		}else {
			System.out.println("= = = 회원 목록 = = =");
			
			for(Customer customer : customers) {
				System.out.println(customer);
			}
		scanner.close();
		}
		
	}//main

}

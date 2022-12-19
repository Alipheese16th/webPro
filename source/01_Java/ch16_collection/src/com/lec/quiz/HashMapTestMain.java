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
			System.out.print("회원가입을 하시겠습니까? (Y/N)");
			yn = scanner.next();
			
			if(yn.equalsIgnoreCase("y")) {
				System.out.print("이름을 입력하세요 : ");
				String name = scanner.next();
				
				System.out.print("전화번호를 입력하세요 : ");
				String tel = scanner.next();
					
				if(customers.get(tel)!=null) {//tel의 키가 존재한다면. 즉 중복값이 있다면
						System.out.println("이미 입력한 전화번호가 존재합니다. 확인부탁드립니다");
							continue;	//해당 회차만 빠져나오고 다시 반복문수행
				}
				
				scanner.nextLine();
				System.out.print("주소를 입력하세요 : ");
				String address = scanner.nextLine();
				
				customers.put(tel,new Customer(name,tel,address));
				System.out.println(name+"님의 회원 가입이 완료되었습니다 감사합니다.");
			}
		}while(!(yn.equalsIgnoreCase("n")));
		
		if(customers.isEmpty()) {	//만약 회원목록이 없다면
			System.out.println("가입한 회원 목록이 없습니다");
		}else {
			System.out.println("= = = 회원 목록 = = =");
			Iterator<String> iterator = customers.keySet().iterator();
			
			while(iterator.hasNext()) {
				String key = iterator.next();
				System.out.println(customers.get(key));
			}
			
		}
		scanner.close();
		
		
	}

}

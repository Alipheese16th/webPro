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
			System.out.print("회원가입을 하시겠습니까? (Y/N)");
			yn = scanner.next();
			
			if(yn.equalsIgnoreCase("y")) {
				System.out.print("이름을 입력하세요 : ");
				String name = scanner.next();
				
				System.out.print("전화번호를 입력하세요 : ");
				String tel = scanner.next();
				
				scanner.nextLine();
				System.out.print("주소를 입력하세요 : ");
				String address = scanner.nextLine();
				
				Customer temp = new Customer(name,tel,address);
				Iterator<Customer> iterator1 = customers.iterator();
				
				boolean check = true;	//중복체크용 불리언변수
				
				while(iterator1.hasNext()) {//iterator를 돌려서 중복값 확인
					if(temp.equals(iterator1.next())) {
						check = false;
					}
				}
				if(check) {//중복값이 없을시 true반환
					customers.add(temp);
					System.out.println(name+"님의 회원 가입이 완료되었습니다 감사합니다.");
				}else {
					System.out.println("★ 오류. 중복된 회원입니다. ★");
				}
			}
		}while(!(yn.equalsIgnoreCase("n")));
		
		if(customers.isEmpty()) {	//만약 회원목록이 없다면
			System.out.println("가입한 회원 목록이 없습니다");
			
		}else {
			System.out.println("= = = 회원 목록 = = =");
			Iterator<Customer> iterator2 = customers.iterator();
			while(iterator2.hasNext()) {
				System.out.println(iterator2.next());
			}
		}
		scanner.close();
		
	}

}

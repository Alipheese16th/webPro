package com.lec.quiz;

import java.util.ArrayList;
import java.util.Scanner;

public class Test {

	public static void main(String[] args) {
		
		ArrayList<Friend> friends = new ArrayList<Friend>();
		friends.add(new Friend("홍길동", "010-9999-1234", "서울시 용산구", "93-05-22"));
		friends.add(new Friend("신길동", "010-9999-9999", "서울시 마포구", "94-04-05"));
		friends.add(new Friend("성춘향", "010-8888-8888", "인천시 남동구", "95-12-05"));
		friends.add(new Friend("이몽룡", "010-7777-7777", "부천시 원미구", "96-11-05"));
		
		Scanner scanner = new Scanner(System.in);
		
		while(true) {
			System.out.print("검색할 주소 앞 글자 2자리 (단, 종료를 원하시면 X를 입력하시오) :");
			String answer = scanner.next();
			
			if(answer.equalsIgnoreCase("x")) {
				break;
			}
			
			boolean check = true;	//체크용 불리언변수
			for (int i = 0; i < friends.size(); i++) {
				String temp = friends.get(i).getAddress().substring(0, 2);
				
				if(answer.equals(temp)) {
					System.out.println(friends.get(i));
					check = false;
				}
			}//for
			
			if(check) {
				System.out.println("해당 지역의 친구는 없습니다");
			}
			System.out.println();
		}//while
		
		scanner.close();
	}//main

}//class

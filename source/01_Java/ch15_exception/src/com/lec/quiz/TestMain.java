package com.lec.quiz;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class TestMain {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
	
		Book book1 = new Book("890ㅁ-01-11","java","홍길동");
		Book book2 = new Book("890ㅁ-01-12","oracle","오길동");
		book1.checkOut("가져감");
		book2.checkOut("빌린자");
		
		book2.setCheckOutDate(new Date(new GregorianCalendar(2022, 10, 1).getTimeInMillis()));
		System.out.println(book1);
		System.out.println(book2);
		
		try {
			book1.checkIn(scanner);
			book2.checkIn(scanner);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
}
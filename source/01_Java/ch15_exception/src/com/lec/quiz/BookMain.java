package com.lec.quiz;

import java.util.Scanner;

public class BookMain {

	public static void main(String[] args) {
		
		Book[] books = {
				new Book("890ㅁ-01-11","java","홍길동"),
				new Book("890ㅁ-01-12","oracle","오길동"),
				new Book("890ㅁ-02-01","mysql","마길동"),
				new Book("890ㅁ-01-01","jdbc","제이디"),
				new Book("890ㅁ-01-01","html","이에이")
		};	
		
		Scanner scanner = new Scanner(System.in);
		String fn;
		int idx;
		String bTitle, borrower; 
		
		do {
			System.out.print("0:전체 대출여부 상태 열람 / 1:대출 /  2:반납 / 그외(문자포함):종료");
			fn = scanner.next();
			switch(fn) {
			case "1": //대출 : 1.책이름입력받는다 2.책조회한다 3.책상태확인 4.대출인입력 5.대출메소드호출
				//1.책이름입력
				System.out.print("대출하고자 하는 책이름은?");
				bTitle = scanner.next(); //white-space 앞까지의 String만 받음
				//2.책조회
				for(idx=0;idx<books.length;idx++) {
					if(bTitle.equals(books[idx].getBookTitle())) {
						break;
					}//if
				}//for
				//idx번째 책으로 진행
				if(idx == books.length) {
					System.out.println("현재 보유하지 않는 도서입니다");
				}else { //books[idx] 도서를 대출
					//3.책상태확인
					if(books[idx].getState() == Book.STATE_BORROWED) {
						System.out.println("현재 대출 중인 도서입니다");
					}else {
						//4.대출인입력
						System.out.print("대출인은? ");
						borrower = scanner.next(); 
						//5.대출메소드호출
						books[idx].checkOut(borrower);
					}//if-상태확인
				}//if-도서조회
				
				break;
			case "2": //반납 : 1.책이름입력 2.책조회 3.반납메소드호출
				
				//1.책이름입력
				System.out.print("반납하고자 하는 책이름은?");
				bTitle = scanner.next(); 
				//2.책조회
				for(idx=0;idx<books.length;idx++) {
					if(bTitle.equals(books[idx].getBookTitle())) {
						break;
					}//if
				}//for
				if(idx == books.length) {
					System.out.println("해당 도서는 본 도서관의 책이 아닙니다");
				}else {
					//3.반납메소드 호출
					try {
						books[idx].checkIn(scanner);
					}catch(Exception e) {
						System.out.println(e.getMessage());
					}
					
				}
				break;
			case "0": // 책list 출력 
				for(Book book : books) {
					System.out.println(book);
				}
			}//switch
		}while(fn.equals("0")||fn.equals("1")||fn.equals("2"));
		System.out.println("종료");
		
	}
}

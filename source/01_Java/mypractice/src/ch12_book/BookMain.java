package ch12_book;

import java.util.Scanner;

public class BookMain {

	public static void main(String[] args) {
		
		Book[] books = {
				new Book("123","java","김씨"),
				new Book("456","html","홍씨"),
				new Book("789","css","신씨"),
				new Book("987","oracle","이씨"),
				new Book("654","mysql","최씨")
		};
		
		Scanner scanner = new Scanner(System.in);
		int fn,idx; // 1.대출 2.반납 3.리스트 0.종료
		String temptitle,borrower,checkDate; //입력받을 책제목, 대출인, 대출날짜 
		
		do {
			System.out.print("명령 - 1.대출  2.반납  3.책list  0.종료");
			fn = scanner.nextInt();
			
			switch(fn) {
			case 1: //대출: 1.책이름입력, 2.책조회, 3.대출가능여부, 4.대출인 대출날짜 입력, 5.대출메소드 
				System.out.print("대출할 책 제목을 입력하세요 :");
				temptitle = scanner.next();
				for (idx = 0; idx < books.length; idx++) {
					if(temptitle.equals(books[idx].getTitle())) {
						break;//조회시 같은이름의 책이 있다면 for문(조회) 나가기
					}
				}//idx로 책 인덱스번호 가져옴
				if(idx == books.length) {
					System.out.println("찾는 도서가 없습니다.");
				}else {
					if(books[idx].getState() == Book.STATE_BORROWED) {
						System.out.println("현재 이 도서는 대출중입니다.");
					}else {
						System.out.print("대출인을 입력하세요 :");
						borrower = scanner.next();
						System.out.print("대출날짜를 입력하세요 :");
						checkDate = scanner.next();
						books[idx].checkout(borrower, checkDate);
					}
				}
				break;
			case 2: //반납: 1.책이름입력 2.책조회 3.반납상태여부 4.반납
				System.out.print("반납할 책 제목을 입력하세요 :");
				temptitle = scanner.next();
				for (idx = 0; idx < books.length; idx++) {
					if(temptitle.equals(books[idx].getTitle())) {
						break;//조회시 같은이름의 책이 있다면 for문(조회) 나가기
					}
				}//idx로 책 인덱스번호 가져옴
				if(idx == books.length) {
					System.out.println("찾는 도서가 없습니다.");
				}else {
					if(books[idx].getState() == Book.STATE_NORMAL) {
						System.out.println("이미 반납 상태인데요?");
					}else {
						books[idx].checkin();
					}
				}
				break;
			case 3:
				for(Book book : books) {
					book.printstate();
				}
				
			}
		}while(fn != 0);
		
		scanner.close();
	}
}

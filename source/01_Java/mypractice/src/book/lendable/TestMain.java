package book.lendable;

import java.util.Scanner;

public class TestMain {

	public static void main(String[] args) {
		
		Book[] books = new Book[5];
		
		for (int i = 0; i < books.length; i++) {
			books[i] = new Book(i,i+"책",i+"길동");
		}
		
		Scanner scanner = new Scanner(System.in);
		String fn;	// 0.리스트  1.대출  2.반납  그외(문자포함).종료
		
		loop :
			do {
			int idx;
			String temp;
			System.out.print("0.리스트  1.대출  2.반납  그외(문자포함).종료");
			fn = scanner.next();
			
			switch(fn) {
			case "0" :
				for (int i = 0; i < books.length; i++) {
					System.out.println(books[i]);
				}
				break;
			case "1" ://대출순서: 1.책이름입력 2.책이름조회 3.대출상태 확인 4.대출인,대출날짜입력 5.대출처리 
				System.out.print("대출할 책 이름을 입력하세요");
				temp = scanner.next();
				for(idx=0; idx<books.length;idx++) {
					if(books[idx].getBookTitle().equals(temp)) {
						break;
					}
				}//idx가 해당도서. 다만 books.length와 같은수면 못찾은거
				if(idx == books.length) {
					System.out.println("해당 도서를 찾지 못하였습니다.");
				}else {
					if(books[idx].isState()) {//대출가능한지 상태확인
						System.out.print("대출인을 입력하세요 ");
						String borrower = scanner.next();
						scanner.nextLine();
						System.out.print("대출 날짜(월  일)를 정수로 입력하세요 ");
						String date = scanner.nextLine();
						String[] drr = date.split(" ");
						int m = Integer.parseInt(drr[0]);
						int d = Integer.parseInt(drr[1]);
						try {
							books[idx].checkout(borrower, m, d);
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}
					}else {
						System.out.println("현재 대출 가능 상태가 아닙니다");
					}
				}//대출처리
				break;
			case "2" ://반납순서: 1.책이름입력 2.책이름조회 3.반납처리
				System.out.print("반납할 책 이름을 입력하세요");
				temp = scanner.next();
				for(idx=0; idx<books.length;idx++) {
					if(books[idx].getBookTitle().equals(temp)) {
						break;
					}
				}//idx가 해당도서. 다만 books.length와 같은수면 못찾은거
				if(idx == books.length) {
					System.out.println("해당 도서를 찾지 못하였습니다.");
				}else {
					try {
						books[idx].checkin(scanner);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				break;
			default :
				break loop;
			}
			
			
			
			
		}while(true);
		
		
		
		
		
		
		
	}

}

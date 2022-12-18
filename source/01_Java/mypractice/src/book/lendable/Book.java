package book.lendable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class Book implements lendableImpl {
	
	private int bookNo;
	private String bookTitle;
	private String writer;
	private String borrower;
	private Calendar checkDate;
	private Calendar mangi;
	private boolean state;
	
	public Book(int bookNo, String bookTitle, String writer) {
		this.bookNo = bookNo;
		this.bookTitle = bookTitle;
		this.writer = writer;
		state = LENDABLE;
	}

	@Override
	public void checkout(String borrower, int m, int d) throws Exception {
		if(state) {
			this.borrower = borrower;
			
			Calendar asdf = Calendar.getInstance();	//그때 당시 날짜
			asdf.set(Calendar.MONTH, m-1);
			asdf.set(Calendar.DATE, d);
			checkDate = asdf;//대출날짜
			
			Calendar asdf2 = (Calendar)asdf.clone();
			asdf2.add(Calendar.DATE, 14);
			mangi = asdf2;	//14일후의 날짜
			state = CANTLEND;
			SimpleDateFormat sdf = new SimpleDateFormat("y년 M월 d일");
			System.out.println(bookTitle+"이 대출처리 되었습니다. 반납 기간은 "+sdf.format(asdf2.getTime())+"까지 부탁드립니다.");
		}else {
			 throw new Exception("현재 "+bookTitle+"은 대출 중입니다.");
		}
	}

	@Override
	public void checkin(Scanner scanner) throws Exception {
		if(state) {
			throw new Exception("이미 반납처리 된 도서입니다. 확인 부탁드립니다.");
		}else {
			SimpleDateFormat sdf = new SimpleDateFormat("M월 d일");
			Calendar asdf = Calendar.getInstance();
			long term = (mangi.getTimeInMillis()-asdf.getTimeInMillis());
			if(term<0){		//만기날짜보다 더 날짜가 지남
				int day = Math.abs((int)Math.floor(term/(1000*60*60*24)));
				System.out.println(sdf.format(mangi.getTime())+"보다 "+day+"일 지났습니다. 연체료를 "+(day*100)+"원 지불하세요. (1일당 100원)");
				
				String temp;
				do {
				System.out.println("연체료를 지불하셨습니까? Y/N");
				temp = scanner.next();
				}while(!(temp.equalsIgnoreCase("y")||temp.equalsIgnoreCase("n")));
				if(temp.equalsIgnoreCase("n")) {
					System.out.println("연체료를 지불하고 오십시오.");
					return;
				}
			}
			System.out.println(bookTitle+"이 반납처리 되었습니다. 이용해주셔서 감사합니다");
			borrower = null;
			checkDate = null;
			mangi = null;
			state = LENDABLE;
			
		}//반납처리
	}//checkin

	
	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
		if(state)
		return "[책번호 = " + bookNo + ", 책제목 = " + bookTitle + ", 저자 = " + writer + (state?", 대출 가능":", 대출 중");
		return "[책번호 = " + bookNo + ", 책제목 =" + bookTitle + ", 저자 =" + writer + (state?", 대출 가능":", 대출 중")
				+", 대출인 = " + borrower+ ", 대출날짜 = " + sdf.format(checkDate.getTime());
	}

	public int getBookNo() {
		return bookNo;
	}

	public String getBookTitle() {
		return bookTitle;
	}

	public String getWriter() {
		return writer;
	}

	public String getBorrower() {
		return borrower;
	}

	public Calendar getCheckDate() {
		return checkDate;
	}

	public Calendar getMangi() {
		return mangi;
	}

	public boolean isState() {
		return state;
	}
	
	

}

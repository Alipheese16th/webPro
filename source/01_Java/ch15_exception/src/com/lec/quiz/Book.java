package com.lec.quiz;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

// Book book = new Book("899ㅁ-01-11", "java","홍길동")
public class Book implements ILendable {
	
	private String bookNo;		//책번호
	private String bookTitle;	//책이름
	private String writer;		//저자
	private String borrower;	//대출인
	private byte state;			//대출가능(0), 대출중(1)
	private Date checkOutDate;//대출일 
	private String yn;
	
	
	public Book(String bookNo, String bookTitle, String writer) {
		this.bookNo = bookNo;
		this.bookTitle = bookTitle;
		this.writer = writer;
	}

	@Override
	public void checkOut(String borrower) {
		if(state == STATE_BORROWED) {//대출중이면 메세지 뿌리고 끝
			System.out.println(bookTitle + " 도서는 대출중입니다");
			return;
		}
		//state가 0(STATE_NORMAL)이라서 대출 진행
		this.borrower = borrower;
		checkOutDate = new Date(); //대출시 날짜 저장
		state = STATE_BORROWED; // "대출중" 상태로 전환
		
		System.out.println(bookTitle + "도서가 대출 처리 되었습니다");
		SimpleDateFormat sdf = new SimpleDateFormat("yy년 MM월 dd일");
		System.out.println("대출인 : "+borrower+"\t대출일:"+sdf.format(checkOutDate));
	}

	
	@Override
	public void checkIn(Scanner scanner) throws Exception {
		
		if(state==STATE_NORMAL) {
			throw new Exception(bookTitle+"은 이미 반납 완료된 책입니다.");
		}
		
		Date now = new Date();	//checkOutDate와 현재시점간의 날짜계산
		long term = now.getTime()-checkOutDate.getTime();
		int day = (int)(term/(1000*60*60*24));
		
		if(day>14) 
			{	// 연체료 출력 14일기준 연체료  -> 연체료냈는지 여부에 따라  y or n 
			int pay = (day-14)*100;
			System.out.println(bookTitle+"도서가 대출 연체 되었습니다. 연체료 일일 100원 부가해서 내셔야할 연체료는 "+pay+"원");
			do {
				System.out.print(bookTitle+"의 연체료("+pay+"원)를 내셨나요?(Y/N)");
				yn = scanner.next();
			}while(!(yn.equalsIgnoreCase("y")||yn.equalsIgnoreCase("n")));
			
			if(yn.equalsIgnoreCase("n")) {
				System.out.println("연체료를 지불하고 오십시오.");
				return;
			}
		}
			
		borrower = null;	//state가 대출중(1:STATE_BORROWED)이라 반납 처리 
		checkOutDate = null;
		state = STATE_NORMAL;
		System.out.println(bookTitle + "도서가 반납 완료되었습니다");
		
	}
	
	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yy년 MM월 dd일");
		String msg = bookNo + "\t" + bookTitle + "(" + writer + "著) \t-";
		msg += state == STATE_NORMAL ? "대출가능" : "대출 중\t"+sdf.format(checkOutDate);;
		return msg;
		
	}

	public String getBookNo() {
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
	
	public byte getState() {
		return state;
	}

	public void setCheckOutDate(Date date) {
		checkOutDate = date;
	}

}

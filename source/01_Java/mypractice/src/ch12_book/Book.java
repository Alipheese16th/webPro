package ch12_book;

public class Book implements ILendable {
	
	private String no,title,writer,borrower,checkDate;
	private byte state;
	
	
	public Book(String no, String title, String writer) {
		this.no = no;
		this.title = title;
		this.writer = writer;
		
	}

	@Override
	public void checkout(String borrower,String checkDate) {
		if(state == STATE_BORROWED) {
			System.out.println("이 도서는 현재 대출 중입니다."); //return 안해보고 해보자 
		}else {
			this.borrower = borrower;
			this.checkDate = checkDate;
			state = STATE_BORROWED;
			System.out.println(title+" 도서가 대출 처리되었습니다. 이용 감사합니다.");
			System.out.println("대출인 : "+borrower+"\t대출일 : "+checkDate);
		}
	}

	@Override
	public void checkin() {
		if(state == STATE_NORMAL) {
			System.out.println("이미 존재하는 도서입니다.");  //여기도 return안함 
		}else {
			borrower = null;
			checkDate = null;
			state = STATE_NORMAL;
			System.out.println("반납 처리 완료.");
		}
	}

	@Override
	public void printstate() {
		System.out.printf("책번호 : %s , 책제목 : %s , 저자 : %s , -%s\n",no,title,writer,
				state == STATE_NORMAL?"대출가능":"대출불가");
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public byte getState() {
		return state;
	}

	public void setState(byte state) {
		this.state = state;
	}

	public String getBorrower() {
		return borrower;
	}

	public void setBorrower(String borrower) {
		this.borrower = borrower;
	}

	public String getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}
	
	
}

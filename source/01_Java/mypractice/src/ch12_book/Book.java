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
			System.out.println("�� ������ ���� ���� ���Դϴ�."); //return ���غ��� �غ��� 
		}else {
			this.borrower = borrower;
			this.checkDate = checkDate;
			state = STATE_BORROWED;
			System.out.println(title+" ������ ���� ó���Ǿ����ϴ�. �̿� �����մϴ�.");
			System.out.println("������ : "+borrower+"\t������ : "+checkDate);
		}
	}

	@Override
	public void checkin() {
		if(state == STATE_NORMAL) {
			System.out.println("�̹� �����ϴ� �����Դϴ�.");  //���⵵ return���� 
		}else {
			borrower = null;
			checkDate = null;
			state = STATE_NORMAL;
			System.out.println("�ݳ� ó�� �Ϸ�.");
		}
	}

	@Override
	public void printstate() {
		System.out.printf("å��ȣ : %s , å���� : %s , ���� : %s , -%s\n",no,title,writer,
				state == STATE_NORMAL?"���Ⱑ��":"����Ұ�");
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

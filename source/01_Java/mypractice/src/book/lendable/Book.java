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
			
			Calendar asdf = Calendar.getInstance();	//�׶� ��� ��¥
			asdf.set(Calendar.MONTH, m-1);
			asdf.set(Calendar.DATE, d);
			checkDate = asdf;//���⳯¥
			
			Calendar asdf2 = (Calendar)asdf.clone();
			asdf2.add(Calendar.DATE, 14);
			mangi = asdf2;	//14������ ��¥
			state = CANTLEND;
			SimpleDateFormat sdf = new SimpleDateFormat("y�� M�� d��");
			System.out.println(bookTitle+"�� ����ó�� �Ǿ����ϴ�. �ݳ� �Ⱓ�� "+sdf.format(asdf2.getTime())+"���� ��Ź�帳�ϴ�.");
		}else {
			 throw new Exception("���� "+bookTitle+"�� ���� ���Դϴ�.");
		}
	}

	@Override
	public void checkin(Scanner scanner) throws Exception {
		if(state) {
			throw new Exception("�̹� �ݳ�ó�� �� �����Դϴ�. Ȯ�� ��Ź�帳�ϴ�.");
		}else {
			SimpleDateFormat sdf = new SimpleDateFormat("M�� d��");
			Calendar asdf = Calendar.getInstance();
			long term = (mangi.getTimeInMillis()-asdf.getTimeInMillis());
			if(term<0){		//���⳯¥���� �� ��¥�� ����
				int day = Math.abs((int)Math.floor(term/(1000*60*60*24)));
				System.out.println(sdf.format(mangi.getTime())+"���� "+day+"�� �������ϴ�. ��ü�Ḧ "+(day*100)+"�� �����ϼ���. (1�ϴ� 100��)");
				
				String temp;
				do {
				System.out.println("��ü�Ḧ �����ϼ̽��ϱ�? Y/N");
				temp = scanner.next();
				}while(!(temp.equalsIgnoreCase("y")||temp.equalsIgnoreCase("n")));
				if(temp.equalsIgnoreCase("n")) {
					System.out.println("��ü�Ḧ �����ϰ� ���ʽÿ�.");
					return;
				}
			}
			System.out.println(bookTitle+"�� �ݳ�ó�� �Ǿ����ϴ�. �̿����ּż� �����մϴ�");
			borrower = null;
			checkDate = null;
			mangi = null;
			state = LENDABLE;
			
		}//�ݳ�ó��
	}//checkin

	
	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
		if(state)
		return "[å��ȣ = " + bookNo + ", å���� = " + bookTitle + ", ���� = " + writer + (state?", ���� ����":", ���� ��");
		return "[å��ȣ = " + bookNo + ", å���� =" + bookTitle + ", ���� =" + writer + (state?", ���� ����":", ���� ��")
				+", ������ = " + borrower+ ", ���⳯¥ = " + sdf.format(checkDate.getTime());
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

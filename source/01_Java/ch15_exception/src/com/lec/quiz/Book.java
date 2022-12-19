package com.lec.quiz;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

// Book book = new Book("899��-01-11", "java","ȫ�浿")
public class Book implements ILendable {
	
	private String bookNo;		//å��ȣ
	private String bookTitle;	//å�̸�
	private String writer;		//����
	private String borrower;	//������
	private byte state;			//���Ⱑ��(0), ������(1)
	private Date checkOutDate;//������ 
	private String yn;
	
	
	public Book(String bookNo, String bookTitle, String writer) {
		this.bookNo = bookNo;
		this.bookTitle = bookTitle;
		this.writer = writer;
	}

	@Override
	public void checkOut(String borrower) {
		if(state == STATE_BORROWED) {//�������̸� �޼��� �Ѹ��� ��
			System.out.println(bookTitle + " ������ �������Դϴ�");
			return;
		}
		//state�� 0(STATE_NORMAL)�̶� ���� ����
		this.borrower = borrower;
		checkOutDate = new Date(); //����� ��¥ ����
		state = STATE_BORROWED; // "������" ���·� ��ȯ
		
		System.out.println(bookTitle + "������ ���� ó�� �Ǿ����ϴ�");
		SimpleDateFormat sdf = new SimpleDateFormat("yy�� MM�� dd��");
		System.out.println("������ : "+borrower+"\t������:"+sdf.format(checkOutDate));
	}

	
	@Override
	public void checkIn(Scanner scanner) throws Exception {
		
		if(state==STATE_NORMAL) {
			throw new Exception(bookTitle+"�� �̹� �ݳ� �Ϸ�� å�Դϴ�.");
		}
		
		Date now = new Date();	//checkOutDate�� ����������� ��¥���
		long term = now.getTime()-checkOutDate.getTime();
		int day = (int)(term/(1000*60*60*24));
		
		if(day>14) 
			{	// ��ü�� ��� 14�ϱ��� ��ü��  -> ��ü��´��� ���ο� ����  y or n 
			int pay = (day-14)*100;
			System.out.println(bookTitle+"������ ���� ��ü �Ǿ����ϴ�. ��ü�� ���� 100�� �ΰ��ؼ� ���ž��� ��ü��� "+pay+"��");
			do {
				System.out.print(bookTitle+"�� ��ü��("+pay+"��)�� ���̳���?(Y/N)");
				yn = scanner.next();
			}while(!(yn.equalsIgnoreCase("y")||yn.equalsIgnoreCase("n")));
			
			if(yn.equalsIgnoreCase("n")) {
				System.out.println("��ü�Ḧ �����ϰ� ���ʽÿ�.");
				return;
			}
		}
			
		borrower = null;	//state�� ������(1:STATE_BORROWED)�̶� �ݳ� ó�� 
		checkOutDate = null;
		state = STATE_NORMAL;
		System.out.println(bookTitle + "������ �ݳ� �Ϸ�Ǿ����ϴ�");
		
	}
	
	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yy�� MM�� dd��");
		String msg = bookNo + "\t" + bookTitle + "(" + writer + "��) \t-";
		msg += state == STATE_NORMAL ? "���Ⱑ��" : "���� ��\t"+sdf.format(checkOutDate);;
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

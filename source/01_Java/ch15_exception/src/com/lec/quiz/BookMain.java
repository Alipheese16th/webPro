package com.lec.quiz;

import java.util.Scanner;

public class BookMain {

	public static void main(String[] args) {
		
		Book[] books = {
				new Book("890��-01-11","java","ȫ�浿"),
				new Book("890��-01-12","oracle","���浿"),
				new Book("890��-02-01","mysql","���浿"),
				new Book("890��-01-01","jdbc","���̵�"),
				new Book("890��-01-01","html","�̿���")
		};	
		
		Scanner scanner = new Scanner(System.in);
		String fn;
		int idx;
		String bTitle, borrower; 
		
		do {
			System.out.print("0:��ü ���⿩�� ���� ���� / 1:���� /  2:�ݳ� / �׿�(��������):����");
			fn = scanner.next();
			switch(fn) {
			case "1": //���� : 1.å�̸��Է¹޴´� 2.å��ȸ�Ѵ� 3.å����Ȯ�� 4.�������Է� 5.����޼ҵ�ȣ��
				//1.å�̸��Է�
				System.out.print("�����ϰ��� �ϴ� å�̸���?");
				bTitle = scanner.next(); //white-space �ձ����� String�� ����
				//2.å��ȸ
				for(idx=0;idx<books.length;idx++) {
					if(bTitle.equals(books[idx].getBookTitle())) {
						break;
					}//if
				}//for
				//idx��° å���� ����
				if(idx == books.length) {
					System.out.println("���� �������� �ʴ� �����Դϴ�");
				}else { //books[idx] ������ ����
					//3.å����Ȯ��
					if(books[idx].getState() == Book.STATE_BORROWED) {
						System.out.println("���� ���� ���� �����Դϴ�");
					}else {
						//4.�������Է�
						System.out.print("��������? ");
						borrower = scanner.next(); 
						//5.����޼ҵ�ȣ��
						books[idx].checkOut(borrower);
					}//if-����Ȯ��
				}//if-������ȸ
				
				break;
			case "2": //�ݳ� : 1.å�̸��Է� 2.å��ȸ 3.�ݳ��޼ҵ�ȣ��
				
				//1.å�̸��Է�
				System.out.print("�ݳ��ϰ��� �ϴ� å�̸���?");
				bTitle = scanner.next(); 
				//2.å��ȸ
				for(idx=0;idx<books.length;idx++) {
					if(bTitle.equals(books[idx].getBookTitle())) {
						break;
					}//if
				}//for
				if(idx == books.length) {
					System.out.println("�ش� ������ �� �������� å�� �ƴմϴ�");
				}else {
					//3.�ݳ��޼ҵ� ȣ��
					try {
						books[idx].checkIn(scanner);
					}catch(Exception e) {
						System.out.println(e.getMessage());
					}
					
				}
				break;
			case "0": // ålist ��� 
				for(Book book : books) {
					System.out.println(book);
				}
			}//switch
		}while(fn.equals("0")||fn.equals("1")||fn.equals("2"));
		System.out.println("����");
		
	}
}

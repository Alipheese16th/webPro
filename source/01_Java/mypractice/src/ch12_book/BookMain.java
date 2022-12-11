package ch12_book;

import java.util.Scanner;

public class BookMain {

	public static void main(String[] args) {
		
		Book[] books = {
				new Book("123","java","�达"),
				new Book("456","html","ȫ��"),
				new Book("789","css","�ž�"),
				new Book("987","oracle","�̾�"),
				new Book("654","mysql","�־�")
		};
		
		Scanner scanner = new Scanner(System.in);
		int fn,idx; // 1.���� 2.�ݳ� 3.����Ʈ 0.����
		String temptitle,borrower,checkDate; //�Է¹��� å����, ������, ���⳯¥ 
		
		do {
			System.out.print("��� - 1.����  2.�ݳ�  3.ålist  0.����");
			fn = scanner.nextInt();
			
			switch(fn) {
			case 1: //����: 1.å�̸��Է�, 2.å��ȸ, 3.���Ⱑ�ɿ���, 4.������ ���⳯¥ �Է�, 5.����޼ҵ� 
				System.out.print("������ å ������ �Է��ϼ��� :");
				temptitle = scanner.next();
				for (idx = 0; idx < books.length; idx++) {
					if(temptitle.equals(books[idx].getTitle())) {
						break;//��ȸ�� �����̸��� å�� �ִٸ� for��(��ȸ) ������
					}
				}//idx�� å �ε�����ȣ ������
				if(idx == books.length) {
					System.out.println("ã�� ������ �����ϴ�.");
				}else {
					if(books[idx].getState() == Book.STATE_BORROWED) {
						System.out.println("���� �� ������ �������Դϴ�.");
					}else {
						System.out.print("�������� �Է��ϼ��� :");
						borrower = scanner.next();
						System.out.print("���⳯¥�� �Է��ϼ��� :");
						checkDate = scanner.next();
						books[idx].checkout(borrower, checkDate);
					}
				}
				break;
			case 2: //�ݳ�: 1.å�̸��Է� 2.å��ȸ 3.�ݳ����¿��� 4.�ݳ�
				System.out.print("�ݳ��� å ������ �Է��ϼ��� :");
				temptitle = scanner.next();
				for (idx = 0; idx < books.length; idx++) {
					if(temptitle.equals(books[idx].getTitle())) {
						break;//��ȸ�� �����̸��� å�� �ִٸ� for��(��ȸ) ������
					}
				}//idx�� å �ε�����ȣ ������
				if(idx == books.length) {
					System.out.println("ã�� ������ �����ϴ�.");
				}else {
					if(books[idx].getState() == Book.STATE_NORMAL) {
						System.out.println("�̹� �ݳ� �����ε���?");
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

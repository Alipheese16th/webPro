package book.lendable;

import java.util.Scanner;

public class TestMain {

	public static void main(String[] args) {
		
		Book[] books = new Book[5];
		
		for (int i = 0; i < books.length; i++) {
			books[i] = new Book(i,i+"å",i+"�浿");
		}
		
		Scanner scanner = new Scanner(System.in);
		String fn;	// 0.����Ʈ  1.����  2.�ݳ�  �׿�(��������).����
		
		loop :
			do {
			int idx;
			String temp;
			System.out.print("0.����Ʈ  1.����  2.�ݳ�  �׿�(��������).����");
			fn = scanner.next();
			
			switch(fn) {
			case "0" :
				for (int i = 0; i < books.length; i++) {
					System.out.println(books[i]);
				}
				break;
			case "1" ://�������: 1.å�̸��Է� 2.å�̸���ȸ 3.������� Ȯ�� 4.������,���⳯¥�Է� 5.����ó�� 
				System.out.print("������ å �̸��� �Է��ϼ���");
				temp = scanner.next();
				for(idx=0; idx<books.length;idx++) {
					if(books[idx].getBookTitle().equals(temp)) {
						break;
					}
				}//idx�� �ش絵��. �ٸ� books.length�� �������� ��ã����
				if(idx == books.length) {
					System.out.println("�ش� ������ ã�� ���Ͽ����ϴ�.");
				}else {
					if(books[idx].isState()) {//���Ⱑ������ ����Ȯ��
						System.out.print("�������� �Է��ϼ��� ");
						String borrower = scanner.next();
						scanner.nextLine();
						System.out.print("���� ��¥(��  ��)�� ������ �Է��ϼ��� ");
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
						System.out.println("���� ���� ���� ���°� �ƴմϴ�");
					}
				}//����ó��
				break;
			case "2" ://�ݳ�����: 1.å�̸��Է� 2.å�̸���ȸ 3.�ݳ�ó��
				System.out.print("�ݳ��� å �̸��� �Է��ϼ���");
				temp = scanner.next();
				for(idx=0; idx<books.length;idx++) {
					if(books[idx].getBookTitle().equals(temp)) {
						break;
					}
				}//idx�� �ش絵��. �ٸ� books.length�� �������� ��ã����
				if(idx == books.length) {
					System.out.println("�ش� ������ ã�� ���Ͽ����ϴ�.");
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

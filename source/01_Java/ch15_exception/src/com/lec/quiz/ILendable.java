package com.lec.quiz;

import java.util.Scanner;

public interface ILendable {
	
	public byte STATE_BORROWED	= 1 ; //"������"�� �ǹ�
	public byte STATE_NORMAL	= 0 ; //"���Ⱑ��"�� �ǹ�
	
	public void checkOut(String borrower); //����
	public void checkIn(Scanner scanner) throws Exception;	//�ݳ�
	
	
}

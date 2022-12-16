package com.lec.quiz;

import java.util.Scanner;

public interface ILendable {
	
	public byte STATE_BORROWED	= 1 ; //"대출중"을 의미
	public byte STATE_NORMAL	= 0 ; //"대출가능"을 의미
	
	public void checkOut(String borrower); //대출
	public void checkIn(Scanner scanner) throws Exception;	//반납
	
	
}

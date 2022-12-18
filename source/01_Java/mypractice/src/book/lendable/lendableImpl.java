package book.lendable;

import java.util.Scanner;

public interface lendableImpl {
	
	public boolean LENDABLE = true;
	public boolean CANTLEND = false;
	
	public void checkout(String borrower, int m, int d) throws Exception;
	
	public void checkin(Scanner scanner) throws Exception;
	
}

package ch12_book;

public interface ILendable {
	
	public static final byte STATE_BORROWED = 1;
	public static final byte STATE_NORMAL = 0;
	
	public void checkout(String borrower, String checkDate);
	public void checkin();
	public void printstate();
	
}

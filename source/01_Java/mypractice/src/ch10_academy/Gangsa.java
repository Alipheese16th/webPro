package ch10_academy;

public class Gangsa extends Person {
	
	private String subject;
	private static int count;
	
	public Gangsa(String id, String name,String subject) {
		super(id, name);
		this.subject = subject;
		setNo("lecturer"+(++count));
	}
	
	@Override
	public void print() {
		super.print();
		System.out.println("\t(°ú¸ñ)"+subject);
	}
}

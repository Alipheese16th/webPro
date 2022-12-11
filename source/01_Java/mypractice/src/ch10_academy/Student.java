package ch10_academy;

public class Student extends Person {

	private String ban;
	private static int count;
	
	public Student(String id, String name,String ban) {
		super(id, name);
		this.ban = ban;
		setNo("student"+(++count));
	}
	
	@Override
	public void print() {
		super.print();
		System.out.println("\t(¹Ý)"+ban);
	}
	
}

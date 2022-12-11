package ch10_academy;

public class Staff extends Person{
	
	private String department;
	private static int count;
	
	public Staff(String id, String name,String department) {
		super(id, name);
		this.department = department;
		setNo("staff"+(++count));
	}
	
	@Override
	public void print() {
		super.print();
		System.out.println("\t(ºÎ¼­)"+department);
	}
}

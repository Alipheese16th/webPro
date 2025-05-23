package quiz_module;

import quiz_interfaces.GetStudentPay;
import quiz_interfaces.JobStudy;

public class Student extends Person {
	
	private String ban;
	
	public Student(String id, String name,String ban) {
		super(id, name);
		this.ban = ban;
		setJob(new JobStudy());
		setGet(new GetStudentPay());
	}
	
	@Override
	public void print() {
		super.print();
		System.out.println("\t�� : "+ban);
	}

}

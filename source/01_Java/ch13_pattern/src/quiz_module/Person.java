package quiz_module;

import quiz_interfaces.GetImpl;
import quiz_interfaces.JobImpl;

public class Person {
	
	private String id;
	private String name;
	
	private GetImpl get;
	private JobImpl job;
	
	
	public Person(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public void job() {
		job.job();
	}
	
	public void get() {
		get.get();
	}
	
	public void print() {
		System.out.print("ID : "+id+"\t¿Ã∏ß : "+name);
	}

	//getter setter
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setGet(GetImpl get) {
		this.get = get;
	}

	public void setJob(JobImpl job) {
		this.job = job;
	}
	
	
	
}

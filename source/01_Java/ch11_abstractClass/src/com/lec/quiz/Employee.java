package com.lec.quiz;

public abstract class Employee {
	
	private String name;
	
	public Employee(String name) {
		this.name = name;
	}

	public abstract int computePay();
	
	public final int computeIncentive() {
			if(computePay() >= 3000000) {
				return (int)(computePay()*0.05);
			} else {
				return 0;
			}
	}
	
	// getter & setter 
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}

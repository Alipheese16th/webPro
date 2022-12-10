package com.lec.quiz;

public class SalaryEmployee extends Employee {
	
	private int annalSalary;
	
	public SalaryEmployee(String name,int annalSalary) {
		super(name);
		this.annalSalary = annalSalary;
	}

	@Override
	public int computePay() {
		return (int)(annalSalary/14.0);
	}

	//getter & setter 
	public int getAnnalSalary() {
		return annalSalary;
	}

	public void setAnnalSalary(int annalSalary) {
		this.annalSalary = annalSalary;
	}
	
}

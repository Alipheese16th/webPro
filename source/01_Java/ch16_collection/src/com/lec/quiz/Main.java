package com.lec.quiz;

import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		
		ArrayList<Customer> customers = new ArrayList<Customer>();

		customers.add(new Customer("1","1","1"));
		customers.add(new Customer("2","2","2"));
		customers.add(new Customer("3","3","3"));
		customers.add(new Customer("4","4","4"));
		customers.add(new Customer("1","1","1"));
		customers.add(new Customer("1","1","1"));
		customers.add(new Customer("1","1","1"));
		
		System.out.println(customers);
		
		customers.remove(new Customer("1","1","1"));
		customers.remove(new Customer("1","1","1"));
		customers.remove(new Customer("1","1","1"));
		customers.remove(new Customer("1","1","1"));
		
		System.out.println(customers);
	
		
		
	}
}

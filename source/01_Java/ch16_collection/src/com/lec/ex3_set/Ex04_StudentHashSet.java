package com.lec.ex3_set;

import java.util.HashSet;
import java.util.Iterator;

public class Ex04_StudentHashSet {
	public static void main(String[] args) {
		
		Student s1 = new Student(1,"홍길동");
		Student s2 = new Student(1,"홍길동");
		System.out.println(s1.hashCode()+" : "+s2.hashCode());
		System.out.println(s1.equals(s2));
		
		HashSet<Student> students = new HashSet<Student>();
		students.add(new Student(1,"홍길동"));
		students.add(new Student(1,"홍길동"));	//hashCode와 equals가 Override되었기때문에 같은값으로 본다
		students.add(new Student(3,"장보고"));
		students.add(new Student(6,"이순신"));
		students.add(new Student(3,"신길동"));
		students.add(new Student(3,"신길동"));
		System.out.println(students);
		students.remove(new Student(1,"홍길동"));
		System.out.println("홍길동 제거 후 "+students);
		
		Iterator<Student> iterator = students.iterator();
		while(iterator.hasNext()) {
			System.out.println(iterator.next());
		}
		
		
	}
}

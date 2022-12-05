package com.lec.ex1_student;

public class StudentMain {

	public static void main(String[] args) {
		Student s1 = new Student("Á¤¿ì¼º",90,90,90);
		Student s2 = new Student("±èÇÏ´Ã",90,90,91);
		Student s3 = new Student("È²Á¤¹Î",80,80,90);
		Student s4 = new Student("°­µ¿¿ø",80,90,90);
		Student s5 = new Student("À¯¾ÆÀÎ",70,70,90);
		Student[] students = {s1,s2,s3,s4,s5};
		line();
		
		
		
		
		
		
		
	}

	private static void line() {
		for (int i = 0; i < 65; i++) {
			System.out.print('¡á');
		}
		System.out.println();
	}

}

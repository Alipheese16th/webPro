package com.lec.quiz;

public class StudentMain {
	public static void main(String[] args) {
		
		Student[] students = new Student[5];
		students[0] = new Student("���켺",90,80,95);
		students[1] = new Student("���ϴ�",100,80,95);
		students[2] = new Student("Ȳ����",95,80,90);
		students[3] = new Student("������",95,90,99);
		students[4] = new Student("������",90,90,90);
		
		String[] title = { "�̸�", "����", "����", "����", "����", "���" };
		int[] total = new int[5];
		
		line();
		System.out.println("\t\t\t��   ��   ǥ");
		line('-');
		
		System.out.print("  ��ȣ");
		for(String t : title) {
			System.out.print("\t"+t);
		}
		
		System.out.println();
		line('-');
		
		for(Student student : students) {
			System.out.println(student.infoString());
			total[0] += student.getKor();
			total[1] += student.getEng();
			total[2] += student.getMat();
			total[3] += student.getTot();
			total[4] += student.getAvg();
		}
		line('-');
		System.out.print("\t����");
		for(int t : total) {
			System.out.print("\t"+t);
		}
		
		System.out.print("\n\t���");
		for (int t : total) {
			System.out.print("\t"+t/students.length);
		}
		System.out.println();
		line();
		
	}
	
	private static void line() {
		for (int i = 0; i < 65; i++) {
			System.out.print('��');
		}
		System.out.println();
	}
	
	private static void line(char c) {
		for (int i = 0; i < 65; i++) {
			System.out.print(c);
		}
		System.out.println();
	}
}

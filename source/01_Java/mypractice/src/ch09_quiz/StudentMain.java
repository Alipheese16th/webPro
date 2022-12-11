package ch09_quiz;

public class StudentMain {

	public static void main(String[] args) {
		
		Student[] students = {
				new Student("���켺",90,80,95),
				new Student("���ϴ�",100,80,95),
				new Student("Ȳ����",95,80,90),
				new Student("������",95,90,99),
				new Student("������",90,90,90)
		};
		String[] titles = {"   ��ȣ","�̸�","����","����","����","����","���"};
		int[] total = new int[5];
		
		line('��');
		System.out.println("\t\t\t����ǥ");
		line();
		for(String title : titles) {
			System.out.print(title+"\t");
		}
		System.out.println();
		line();
		for(Student student : students) {
			student.print();
			total[0] += student.getKor();
			total[1] += student.getEng();
			total[2] += student.getMat();
			total[3] += student.getTot();
			total[4] += student.getAvg();
		}
		line();
		
		System.out.print("\t����");
		for(int tot : total) {
			System.out.print("\t"+tot);
		}
		
		System.out.println();
		
		System.out.print("\t���");
		for(int tot : total) {
			System.out.print("\t"+(int)Math.round((double)tot/students.length));
		}
		System.out.println();
		line('��');
		
	}
	
	public static void line() {
		for (int i = 0; i < 55; i++) {
			System.out.print('-');
		}
		System.out.println();
	}
	public static void line(char c) {
		for (int i = 0; i < 55; i++) {
			System.out.print(c);
		}
		System.out.println();
	}
}

package ch09_quiz;

public class StudentMain {

	public static void main(String[] args) {
		
		Student[] students = {
				new Student("정우성",90,80,95),
				new Student("김하늘",100,80,95),
				new Student("황정민",95,80,90),
				new Student("강동원",95,90,99),
				new Student("유아인",90,90,90)
		};
		String[] titles = {"   번호","이름","국어","영어","수학","총점","평균"};
		int[] total = new int[5];
		
		line('■');
		System.out.println("\t\t\t성적표");
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
		
		System.out.print("\t총점");
		for(int tot : total) {
			System.out.print("\t"+tot);
		}
		
		System.out.println();
		
		System.out.print("\t평균");
		for(int tot : total) {
			System.out.print("\t"+(int)Math.round((double)tot/students.length));
		}
		System.out.println();
		line('■');
		
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

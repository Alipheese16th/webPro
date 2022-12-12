package quiz_module;

public class TestMain {

	public static void main(String[] args) {
		
		Student st1 = new Student("ere","홍길동","국비자바");
		Student st2 = new Student("dbd", "김길동", "자바단기");
		Lecturer l1 = new Lecturer("aaa", "유길동", "빅데이터");
		Lecturer l2 = new Lecturer("bdc", "홍길동", "웹프로그래밍");
		Staff s1  = new Staff("sss", "유용현", "관리");
		Person[] person = {st1, st2, l1, l2, s1};
		
		System.out.println("업무시간이니 일합시다★");
		for(Person p : person) {
			p.job();
		}
		
		line();
		System.out.println("지급받아요★");
		for(int i=0 ; i<person.length ; i++) {
			person[i].get();
		}
		
		line();
		System.out.println("출력합니다★");
		for(Person p : person) {
			p.print();
		}
		
	}
	//보기 편하게 하려고 라인 메소드 
	public static void line() {
		for (int i = 0; i < 60; i++) {
			System.out.print('-');
		}System.out.println();
	}
	
}

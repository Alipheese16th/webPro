package quiz_module;

public class TestMain {

	public static void main(String[] args) {
		
		Student st1 = new Student("ere","ȫ�浿","�����ڹ�");
		Student st2 = new Student("dbd", "��浿", "�ڹٴܱ�");
		Lecturer l1 = new Lecturer("aaa", "���浿", "������");
		Lecturer l2 = new Lecturer("bdc", "ȫ�浿", "�����α׷���");
		Staff s1  = new Staff("sss", "������", "����");
		Person[] person = {st1, st2, l1, l2, s1};
		
		System.out.println("�����ð��̴� ���սô١�");
		for(Person p : person) {
			p.job();
		}
		
		line();
		System.out.println("���޹޾ƿ��");
		for(int i=0 ; i<person.length ; i++) {
			person[i].get();
		}
		
		line();
		System.out.println("����մϴ١�");
		for(Person p : person) {
			p.print();
		}
		
	}
	//���� ���ϰ� �Ϸ��� ���� �޼ҵ� 
	public static void line() {
		for (int i = 0; i < 60; i++) {
			System.out.print('-');
		}System.out.println();
	}
	
}

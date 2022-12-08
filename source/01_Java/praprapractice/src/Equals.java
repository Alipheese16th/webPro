
class Student{
	String name;
	Student(String name){
		this.name = name;
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.name == ((Student)obj).name;
	}//equals�� ���� ��ü���� ���ϴ� Object�޼ҵ����� �̷��� ���ϰ��� �������̵����� �ٲ��ָ�
	//���� ���ϴ� ���밪�� �����ټ� �ִ�. 
	
	@Override
	public String toString() { 
		return name; 
	}//toString�޼ҵ�� sysout�� �ڱ� ��ü�̸��� �־ ������ ��. ������ ��ü �ּҰ� ���´�.
	//�׷��� �̷��� �������̵����� ������ �ٲٸ� �ڱ� ��ü�̸��� ������ �̻ڰ� ������ ���´�.
}

public class Equals {
	public static void main(String[] args) {
		Student s1 = new Student("�ȳ��ϼ���");
		Student s2 = new Student("�ȳ��ϼ���");
		System.out.println(s1 == s2);
		System.out.println(s1.equals(s2));
		System.out.println(s1);
		System.out.println(s2);
	}
}

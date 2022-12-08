
class Student{
	String name;
	Student(String name){
		this.name = name;
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.name == ((Student)obj).name;
	}//equals는 원래 객체값을 비교하는 Object메소드지만 이렇게 리턴값을 오버라이딩으로 바꿔주면
	//내가 원하는 내용값을 비교해줄수 있다. 
	
	@Override
	public String toString() { 
		return name; 
	}//toString메소드는 sysout에 자기 객체이름을 넣어도 나오는 값. 보통은 객체 주소가 나온다.
	//그러나 이렇게 오버라이딩으로 내용을 바꾸면 자기 객체이름을 넣으면 이쁘게 내용이 나온다.
}

public class Equals {
	public static void main(String[] args) {
		Student s1 = new Student("안녕하세요");
		Student s2 = new Student("안녕하세요");
		System.out.println(s1 == s2);
		System.out.println(s1.equals(s2));
		System.out.println(s1);
		System.out.println(s2);
	}
}

package singleton2;

public class SecondClass {
	
	public SecondClass() {
		System.out.println("secondClass ������ ���� ��");
		SingletonClass singletonObject = SingletonClass.getInstance();
		System.out.println("�̱��� ��ü�� i���� "+singletonObject.getI());
		
	}
	
	
	
}

/*
������ �ּ� (������ ����)
 */

// ���� �ּ�

public class Hello {
	//�ڹ� ���ϸ��� Ŭ���� ��� ����. �빮�ڷ� ����. 
	//JVM �ڹٰ���ӽ��� �����ϴ�  main�Լ�
	public static void main(String[] args) {

		System.out.println("Hello , World");

		int age = 10;
		String adr = "Seoul";
		float f = 1.23456789f;
		double d = 1.23456789;

		System.out.printf("Im in %s and age is %d%n",adr,age);
		System.out.printf("%f%n",f);
		System.out.printf("%.3f%n",f);
		System.out.printf("%10.8f%n",d);
		System.out.printf("%e%n",d);
		System.out.printf("%o%n",age);
		System.out.printf("%x%n",age);
		

	}
}

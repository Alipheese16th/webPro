package strategy1.step2;

public class Robot {
	
	public void actionWalk() {
		System.out.println("���� �� �ֽ��ϴ�.");
	}
	
	public void actionRun() {
		System.out.println("�޸� �� �ֽ��ϴ�.");
	}
	
	public void shape() {
		String className = getClass().getName();  //Ŭ���� �̸� ("strategy1.step2.Robot")
		int idx = className.lastIndexOf("."); // 15��° idx
		String name = className.substring(idx+1); //16��°���� �����ض� "Robot"
		System.out.println(name+"������ ��, �ٸ�, ����, �Ӹ��� �ֽ��ϴ�.");
	}
	
	
}

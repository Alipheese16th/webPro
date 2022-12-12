package strategy1.step4.modularization;

public abstract class Robot {
	
	public void actionWalk() {
		System.out.println("걸을 수 있습니다.");
	}
	
	public void actionRun() {
		System.out.println("달릴 수 있습니다.");
	}
	
	public void shape() {
		String className = getClass().getName();  //클래스 이름 ("strategy1.step2.Robot")
		int idx = className.lastIndexOf("."); // 15번째 idx
		String name = className.substring(idx+1); //16번째부터 추출해라 "Robot"
		System.out.println(name+"외형은 팔, 다리, 몸통, 머리가 있습니다.");
	}
	
	public abstract void actionFly();
	
	public abstract void actionMissile();
	
	public abstract void actionknife();
	
}

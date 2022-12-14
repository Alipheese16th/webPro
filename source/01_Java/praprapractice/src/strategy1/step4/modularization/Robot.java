package strategy1.step4.modularization;

import strategy1.step4.interfaces.FlyImpl;
import strategy1.step4.interfaces.KnifeImpl;
import strategy1.step4.interfaces.MissileImpl;

public class Robot {
	
	private FlyImpl fly;
	private MissileImpl missile;
	private KnifeImpl knife;
	
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
	
	
	public void actionFly() {
		fly.fly();
	}
	
	public void actionMissile() {
		missile.missile();
	}
	
	public void actionknife() {
		knife.knife();
	}

	public FlyImpl getFly() {
		return fly;
	}

	public void setFly(FlyImpl fly) {
		this.fly = fly;
	}

	public MissileImpl getMissile() {
		return missile;
	}

	public void setMissile(MissileImpl missile) {
		this.missile = missile;
	}

	public KnifeImpl getKnife() {
		return knife;
	}

	public void setKnife(KnifeImpl knife) {
		this.knife = knife;
	}
	
	
	
}

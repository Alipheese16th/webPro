package strategy1.step4.modularization;

import strategy1.step4.interfaces.FlyHigh;
import strategy1.step4.interfaces.KnifeToy;

public class TestMain {

	public static void main(String[] args) {
		
		SuperRobot superRobot = new SuperRobot();
		StandardRobot standardRobot = new StandardRobot();
		LowRobot lowRobot = new LowRobot();
		
		Robot[] robots = {
				superRobot,standardRobot,lowRobot
				};
		
		for(Robot robot : robots) {
			robot.shape();
			robot.actionRun();
			robot.actionWalk();
			robot.actionFly();
			robot.actionMissile();
			robot.actionknife();
			
		}
		System.out.println("SuperRobot�� ���� ��ǰ�� ���� ���� ���� ���� ��� ���׷��̵�");
		superRobot.setFly(new FlyHigh());
		robots[0].shape();
		robots[0].actionFly();
		System.out.println("LowRobot�� knife��ǰ�� '�峭�� ���� �ֽ��ϴ�'������� ���׷��̵�");
		lowRobot.setKnife(new KnifeToy());
		robots[2].shape();
		robots[2].actionknife();
	}

}

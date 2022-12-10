
abstract class Unit2{
	int x, y;
	abstract void move(int x, int y );
	void stop() {
		System.out.println("����ϴ�.");
	}
}
interface Fightable {
	 void move (int x, int y);
	void attack (Fightable f);
}

class Fighter extends Unit2 implements Fightable{

	@Override
	public void move(int x, int y) {
		System.out.println("["+x+","+y+"]�� �̵�");
	}
	@Override
	public void attack(Fightable f) {
		System.out.println(f+"�� ����");
	}

}

public class ininini {
	public static void main(String[] args) {
//		Fighter f = new Fighter();
		Unit2 u = new Fighter();	
		Fightable f = new Fighter();
		u.move(100, 200);
		u.stop();
//		u.attack(f);	//Unit2���� attack()�� ��� ȣ��Ұ�
		f.move(100, 200);
		f.attack(f);
//		f.stop();		//Fightable���� stop()�� ��� ȣ��Ұ� 
		
		
		
	}
}

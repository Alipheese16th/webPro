
abstract class Unit2{
	int x, y;
	abstract void move(int x, int y );
	void stop() {
		System.out.println("멈춥니다.");
	}
}
interface Fightable {
	 void move (int x, int y);
	void attack (Fightable f);
}

class Fighter extends Unit2 implements Fightable{

	@Override
	public void move(int x, int y) {
		System.out.println("["+x+","+y+"]로 이동");
	}
	@Override
	public void attack(Fightable f) {
		System.out.println(f+"를 공격");
	}

}

public class ininini {
	public static void main(String[] args) {
//		Fighter f = new Fighter();
		Unit2 u = new Fighter();	
		Fightable f = new Fighter();
		u.move(100, 200);
		u.stop();
//		u.attack(f);	//Unit2에는 attack()이 없어서 호출불가
		f.move(100, 200);
		f.attack(f);
//		f.stop();		//Fightable에는 stop()이 없어서 호출불가 
		
		
		
	}
}

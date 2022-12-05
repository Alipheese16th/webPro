
public class prac1 {
	
	long a, b;  // 인스턴스 변수 
	
	long add() {   // 인스턴스메소드는 인스턴스변수 사용 가능 
		return a+b;
	}
	
	static long add(long a, long b) {  //static메소드는 인스턴스변수를 사용할수 없다
		return a+b;
	}

	
	public static void main(String[] args) {

	}

}

package volume;

//인터페이스 - static final 변수(상수)와 추상메소드  (예외: default메소드, static메소드)

public interface IVolume {
	
	public void volumeUp();
	public void volumeUp(int level);
	public void volumeDown();
	public void volumeDown(int level);
	
	public default void setMute(boolean mute) {	// default메소드
		if(mute)
			System.out.println("무음");
		else
			System.out.println("무음해제");
	};
	
	public static void changeBattery() {	// static 메소드 
		System.out.println("건전지를 교환합니다");
	}
	
}

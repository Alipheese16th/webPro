package volume;

public class TV implements IVolume {

	private final int TV_MAX_VOLUME = 30;
	private final int TV_MIN_VOLUME = 0;
	private int volumeLevel;
	
	public TV() {
		volumeLevel = 5;
	}
	
	public TV(int volumeLevel) {
		this.volumeLevel = volumeLevel;
	}


	@Override
	public void volumeUp() {
		if(volumeLevel<TV_MAX_VOLUME) {
			volumeLevel++ ;
			System.out.println("TV ������ 1��ŭ �÷� ����"+volumeLevel);
		}else {
			System.out.println("TV ������ �ִ�ġ("+volumeLevel+")���� �ø��� ���߽��ϴ�.");
		}
	}

	@Override
	public void volumeUp(int level) {
		if(volumeLevel<TV_MAX_VOLUME) {
			int tempLevel = TV_MAX_VOLUME - volumeLevel;
					if(level > tempLevel) {
						volumeLevel = TV_MAX_VOLUME;
						System.out.println("TV ������ "+tempLevel+"��ŭ �÷� ����"+volumeLevel);
					}
					else {
						volumeLevel += level ;
						System.out.println("TV ������ "+level+"��ŭ �÷� ����"+volumeLevel);
					}
		}else {
			System.out.println("TV ������ ���� �ִ�ġ("+volumeLevel+")�Դϴ�.");
		}
	}

	@Override
	public void volumeDown() {
		if(volumeLevel>TV_MIN_VOLUME) {
			volumeLevel-- ;
			System.out.println("TV ������ 1��ŭ ���� ����"+volumeLevel);
		}else {
			System.out.println("TV ������ �ּ�ġ("+volumeLevel+")���� ������ ���߽��ϴ�.");
		}
	}

	@Override
	public void volumeDown(int level) {
		if(volumeLevel>TV_MIN_VOLUME) {
			int tempLevel = volumeLevel - TV_MIN_VOLUME;
					if(level > tempLevel) {
						volumeLevel = TV_MIN_VOLUME;
						System.out.println("TV ������ "+tempLevel+"��ŭ ���� ����"+volumeLevel);
					}
					else {
						volumeLevel -= level ;
						System.out.println("TV ������ "+level+"��ŭ ���� ����"+volumeLevel);
					}
		}else {
			System.out.println("TV ������ ���� �ּ�ġ("+volumeLevel+")�Դϴ�.");
		}
	}

}

package volume;

public class Speaker implements IVolume {

	private final int SPEAKER_MAX_VOLUME = 50;
	private final int SPEAKER_MIN_VOLUME = 2;
	private int volumeLevel;
	
	public Speaker() {
		volumeLevel = 10;
	}
	public Speaker(int volumeLevel) {
		this.volumeLevel = volumeLevel;
	}

	@Override
	public void volumeUp() {
		if(volumeLevel<SPEAKER_MAX_VOLUME) {
			volumeLevel++ ;
			System.out.println("����Ŀ ������ 1��ŭ �÷� ����"+volumeLevel);
		}else {
			System.out.println("����Ŀ ������ �ִ�ġ("+volumeLevel+")���� �ø��� ���߽��ϴ�.");
		}
	}

	@Override
	public void volumeUp(int level) {
		if(volumeLevel<SPEAKER_MAX_VOLUME) {
			int tempLevel = SPEAKER_MAX_VOLUME - volumeLevel;
					if(level > tempLevel) {
						volumeLevel = SPEAKER_MAX_VOLUME;
						System.out.println("����Ŀ ������ "+tempLevel+"��ŭ �÷� ����"+volumeLevel);
					}
					else {
						volumeLevel += level ;
						System.out.println("����Ŀ ������ "+level+"��ŭ �÷� ����"+volumeLevel);
					}
		}else {
			System.out.println("����Ŀ ������ ���� �ִ�ġ("+volumeLevel+")�Դϴ�.");
		}
	}

	@Override
	public void volumeDown() {
		if(volumeLevel>SPEAKER_MIN_VOLUME) {
			volumeLevel-- ;
			System.out.println("����Ŀ ������ 1��ŭ ���� ����"+volumeLevel);
		}else {
			System.out.println("����Ŀ ������ �ּ�ġ("+volumeLevel+")���� ������ ���߽��ϴ�.");
		}
	}

	@Override
	public void volumeDown(int level) {
		if(volumeLevel>SPEAKER_MIN_VOLUME) {
			int tempLevel = volumeLevel - SPEAKER_MIN_VOLUME;
					if(level > tempLevel) {
						volumeLevel = SPEAKER_MIN_VOLUME;
						System.out.println("����Ŀ ������ "+tempLevel+"��ŭ ���� ����"+volumeLevel);
					}
					else {
						volumeLevel -= level ;
						System.out.println("����Ŀ ������ "+level+"��ŭ ���� ����"+volumeLevel);
					}
		}else {
			System.out.println("����Ŀ ������ ���� �ּ�ġ("+volumeLevel+")�Դϴ�.");
		}
	}
	
}

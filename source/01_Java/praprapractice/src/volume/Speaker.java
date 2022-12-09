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
			System.out.println("스피커 볼륨을 1만큼 올려 현재"+volumeLevel);
		}else {
			System.out.println("스피커 볼륨이 최대치("+volumeLevel+")여서 올리지 못했습니다.");
		}
	}

	@Override
	public void volumeUp(int level) {
		if(volumeLevel<SPEAKER_MAX_VOLUME) {
			int tempLevel = SPEAKER_MAX_VOLUME - volumeLevel;
					if(level > tempLevel) {
						volumeLevel = SPEAKER_MAX_VOLUME;
						System.out.println("스피커 볼륨을 "+tempLevel+"만큼 올려 현재"+volumeLevel);
					}
					else {
						volumeLevel += level ;
						System.out.println("스피커 볼륨을 "+level+"만큼 올려 현재"+volumeLevel);
					}
		}else {
			System.out.println("스피커 볼륨은 현재 최대치("+volumeLevel+")입니다.");
		}
	}

	@Override
	public void volumeDown() {
		if(volumeLevel>SPEAKER_MIN_VOLUME) {
			volumeLevel-- ;
			System.out.println("스피커 볼륨을 1만큼 내려 현재"+volumeLevel);
		}else {
			System.out.println("스피커 볼륨이 최소치("+volumeLevel+")여서 내리지 못했습니다.");
		}
	}

	@Override
	public void volumeDown(int level) {
		if(volumeLevel>SPEAKER_MIN_VOLUME) {
			int tempLevel = volumeLevel - SPEAKER_MIN_VOLUME;
					if(level > tempLevel) {
						volumeLevel = SPEAKER_MIN_VOLUME;
						System.out.println("스피커 볼륨을 "+tempLevel+"만큼 내려 현재"+volumeLevel);
					}
					else {
						volumeLevel -= level ;
						System.out.println("스피커 볼륨을 "+level+"만큼 내려 현재"+volumeLevel);
					}
		}else {
			System.out.println("스피커 볼륨은 현재 최소치("+volumeLevel+")입니다.");
		}
	}
	
}

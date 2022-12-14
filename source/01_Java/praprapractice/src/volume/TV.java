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
			System.out.println("TV 볼륨을 1만큼 올려 현재"+volumeLevel);
		}else {
			System.out.println("TV 볼륨이 최대치("+volumeLevel+")여서 올리지 못했습니다.");
		}
	}

	@Override
	public void volumeUp(int level) {
		if(volumeLevel<TV_MAX_VOLUME) {
			int tempLevel = TV_MAX_VOLUME - volumeLevel;
					if(level > tempLevel) {
						volumeLevel = TV_MAX_VOLUME;
						System.out.println("TV 볼륨을 "+tempLevel+"만큼 올려 현재"+volumeLevel);
					}
					else {
						volumeLevel += level ;
						System.out.println("TV 볼륨을 "+level+"만큼 올려 현재"+volumeLevel);
					}
		}else {
			System.out.println("TV 볼륨은 현재 최대치("+volumeLevel+")입니다.");
		}
	}

	@Override
	public void volumeDown() {
		if(volumeLevel>TV_MIN_VOLUME) {
			volumeLevel-- ;
			System.out.println("TV 볼륨을 1만큼 내려 현재"+volumeLevel);
		}else {
			System.out.println("TV 볼륨이 최소치("+volumeLevel+")여서 내리지 못했습니다.");
		}
	}

	@Override
	public void volumeDown(int level) {
		if(volumeLevel>TV_MIN_VOLUME) {
			int tempLevel = volumeLevel - TV_MIN_VOLUME;
					if(level > tempLevel) {
						volumeLevel = TV_MIN_VOLUME;
						System.out.println("TV 볼륨을 "+tempLevel+"만큼 내려 현재"+volumeLevel);
					}
					else {
						volumeLevel -= level ;
						System.out.println("TV 볼륨을 "+level+"만큼 내려 현재"+volumeLevel);
					}
		}else {
			System.out.println("TV 볼륨은 현재 최소치("+volumeLevel+")입니다.");
		}
	}

}

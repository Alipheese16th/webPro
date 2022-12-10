package volume;

public class TestMain {

	public static void main(String[] args) {
		IVolume[] devices = {new TV(5), new Speaker()};
		for(IVolume device : devices) {
			device.volumeUp();
			device.volumeUp();
			device.volumeUp(50);
			device.volumeUp(50);
			device.volumeUp(50);
			device.setMute(true);
			IVolume.changeBattery();
			device.setMute(false);
			device.volumeDown();
			device.volumeDown();
			device.volumeDown(50);
			device.volumeDown(50);
			device.volumeDown(50);
			System.out.println("=========================================");
		}
		
		
		
		
	}
}
